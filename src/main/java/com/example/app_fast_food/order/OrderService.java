package com.example.app_fast_food.order;

import com.example.app_fast_food.bonus.BonusMapper;
import com.example.app_fast_food.bonus.BonusService;
import com.example.app_fast_food.bonus.dto.bonus.BonusResponseDto;
import com.example.app_fast_food.common.response.ApiMessageResponse;
import com.example.app_fast_food.discount.entity.Discount;
import com.example.app_fast_food.exception.AlreadyAddedToBasketException;
import com.example.app_fast_food.exception.EntityNotFoundException;
import com.example.app_fast_food.exception.UserBasketNotFoundException;
import com.example.app_fast_food.order.dto.OrderResponseDto;
import com.example.app_fast_food.order.entity.Order;
import com.example.app_fast_food.order.entity.OrderStatus;
import com.example.app_fast_food.orderitem.dto.OrderItemCreateRequestDTO;
import com.example.app_fast_food.orderitem.entity.OrderItem;
import com.example.app_fast_food.product.ProductMapper;
import com.example.app_fast_food.product.ProductRepository;
import com.example.app_fast_food.product.dto.ProductResponseDto;
import com.example.app_fast_food.product.entity.Product;
import com.example.app_fast_food.user.UserRepository;
import com.example.app_fast_food.user.dto.AuthDto;
import com.example.app_fast_food.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository repository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    private final OrderMapper mapper;
    private final ProductMapper productMapper;
    private final BonusMapper bonusMapper;

    private final BonusService bonusService;

    public static final String PRODUCT_ENTITY = "Product";
    public static final String BASKET_ENTITY = "Basket";
    public static final String BONUS_ENTITY = "Bonus";

    private static final String USER_BASKET_NOT_FOUND = "Basket with user id `%s` not found";

    public OrderResponseDto addProduct(OrderItemCreateRequestDTO dto, AuthDto auth) {
        Order order = getOrCreateBasket(auth);

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException(
                        PRODUCT_ENTITY, dto.getProductId().toString()));

        Optional<OrderItem> existing = order.getOrderItems().stream()
                .filter(oi -> oi.getProduct().getId().equals(dto.getProductId()))
                .findFirst();

        if (existing.isPresent()) {
            throw new AlreadyAddedToBasketException("Product already added to basket");
        }

        OrderItem orderItem = new OrderItem(null, dto.getQuantity(), product, order);
        order.getOrderItems().add(orderItem);

        calculateOrderPrices(order);

        repository.save(order);
        return mapper.toResponseDto(order);
    }

    public OrderResponseDto updateQuantity(AuthDto auth, UUID productId, int quantity) {
        Order order = repository.findBasketByUserId(
                auth.getId())
                .orElseThrow(
                        () -> new UserBasketNotFoundException(USER_BASKET_NOT_FOUND.formatted(auth.getId())));

        OrderItem orderItem = order.getOrderItems().stream()
                .filter(o -> o.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(
                        () -> new EntityNotFoundException(PRODUCT_ENTITY, productId.toString()));

        if (quantity <= 0) {
            order.getOrderItems().removeIf(i -> i.getProduct().getId().equals(productId));
        } else {
            orderItem.setQuantity(quantity);
        }

        calculateOrderPrices(order);
        repository.save(order);

        return mapper.toResponseDto(order);
    }

    public Order getOrCreateBasket(AuthDto auth) {
        return repository.findBasketByUserId(auth.getId())
                .orElseGet(() -> {
                    User user = userRepository.findById(auth.getId())
                            .orElseThrow(() -> new EntityNotFoundException("User", auth.getId().toString()));

                    Order order = new Order();
                    order.setUser(user);
                    order.setCreatedAt(LocalDateTime.now());
                    order.setStatus(OrderStatus.BASKET);

                    order.setDiscountAmount(BigDecimal.ZERO);
                    order.setTotalPrice(BigDecimal.ZERO);
                    order.setFinalPrice(BigDecimal.ZERO);

                    return repository.save(order);
                });
    }

    public List<OrderResponseDto> getOrderByStatus(String status) {
        try {
            OrderStatus orderStatus = OrderStatus.valueOf(status);
            List<Order> orders = repository.findByStatus(orderStatus);

            return orders.stream()
                    .map(mapper::toResponseDto)
                    .toList();

        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid order status `%s`".formatted(status));
        }
    }

    public ApiMessageResponse deleteBasket(AuthDto auth) {
        repository.deleteOrderByUserIdAndStatus(auth.getId(), OrderStatus.BASKET);
        return new ApiMessageResponse("Basket successfully deleted");
    }

    public void confirmOrder(AuthDto auth) {
        Order order = repository
                .findBasketByUserId(auth.getId())
                .orElseThrow(() -> new EntityNotFoundException(BASKET_ENTITY, auth.getId().toString()));

        if (order.getOrderItems().isEmpty()) {
            throw new IllegalStateException("Basket is empty");
        }

        calculateOrderPrices(order);
        order.setStatus(OrderStatus.IN_PROCESS);
        repository.save(order);

    }

    public OrderResponseDto removeProduct(AuthDto auth, UUID productId) {
        Order order = repository.findBasketByUserId(auth.getId())
                .orElseThrow(
                        () -> new UserBasketNotFoundException(USER_BASKET_NOT_FOUND.formatted(auth.getId())));

        boolean removed = order.getOrderItems().removeIf(oi -> oi.getProduct().getId().equals(productId));

        if (!removed) {
            throw new EntityNotFoundException(PRODUCT_ENTITY, productId.toString());
        }

        calculateOrderPrices(order);
        repository.save(order);

        return mapper.toResponseDto(order);
    }

    public ProductResponseDto selectBonus(AuthDto auth, UUID productBonusId) {
        Order o = repository.findBasketByUserId(auth.getId())
                .orElseThrow(() -> new UserBasketNotFoundException(
                        USER_BASKET_NOT_FOUND.formatted(auth.getId())));

        Product p = productRepository.findById(productBonusId)
                .orElseThrow(() -> new EntityNotFoundException(PRODUCT_ENTITY,
                        productBonusId.toString()));

        OrderItem item = new OrderItem(null, 1, p, o);

        o.getOrderItems().add(item);
        repository.save(o);

        return productMapper.toResponseDTO(p);
    }

    public List<BonusResponseDto> getAvailableBonuses(AuthDto auth) {
        Order order = repository.findBasketByUserId(
                auth.getId()).orElseThrow(
                        () -> new UserBasketNotFoundException(
                                USER_BASKET_NOT_FOUND.formatted(auth.getId())));

        User user = userRepository.findById(auth.getId())
                .orElseThrow(() -> new EntityNotFoundException("User", auth.getId().toString()));

        return bonusService.getAvailableOrderBonuses(
                user, order).stream().map(bonusMapper::toResponseDto).toList();
    }

    public List<OrderResponseDto> getAll() {
        return repository.findAll().stream().map(mapper::toResponseDto).toList();

    }

    public OrderResponseDto getById(@NonNull UUID id) {
        Order order = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Order", id.toString()));
        return mapper.toResponseDto(order);
    }

    public OrderResponseDto getBasket(AuthDto auth) {
        return mapper.toResponseDto(getOrCreateBasket(auth));
    }

    private void applyDiscounts(
            Order order,
            List<Discount> holidayDiscounts,
            Set<Discount> productQuantityBasedDiscounts) {

        BigDecimal orderTotal = BigDecimal.ZERO;

        for (OrderItem item : order.getOrderItems()) {
            BigDecimal price = item.getProduct().getPrice();
            BigDecimal qty = BigDecimal.valueOf(item.getQuantity());
            orderTotal = orderTotal.add(price.multiply(qty));
        }

        order.setTotalPrice(orderTotal);

        BigDecimal totalDiscount = BigDecimal.ZERO;

        for (Discount discount : holidayDiscounts) {
            BigDecimal percent = BigDecimal.valueOf(discount.getPercentage()).divide(BigDecimal.valueOf(100));

            totalDiscount = totalDiscount.add(orderTotal.multiply(percent));
        }

        order.setDiscountAmount(totalDiscount);
        order.setFinalPrice(orderTotal.subtract(totalDiscount));
    }

    private void calculateOrderPrices(Order order) {
        // write down it already
    }
}
