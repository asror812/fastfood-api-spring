package com.example.app_fast_food.order;

import com.example.app_fast_food.bonus.BonusMapper;
import com.example.app_fast_food.bonus.BonusRepository;
import com.example.app_fast_food.bonus.BonusService;
import com.example.app_fast_food.bonus.dto.bonus.BonusResponseDto;
import com.example.app_fast_food.bonus.entity.Bonus;
import com.example.app_fast_food.bonus.entity.BonusProductLink;
import com.example.app_fast_food.common.response.ApiMessageResponse;
import com.example.app_fast_food.discount.entity.Discount;
import com.example.app_fast_food.discount.entity.DiscountType;
import com.example.app_fast_food.exception.AlreadyAddedToBasketException;
import com.example.app_fast_food.exception.EntityNotFoundException;
import com.example.app_fast_food.exception.UserBasketNotFoundException;
import com.example.app_fast_food.order.dto.ChosenOrderDto;
import com.example.app_fast_food.order.dto.OrderResponseDto;
import com.example.app_fast_food.order.entity.Order;
import com.example.app_fast_food.order.entity.OrderStatus;
import com.example.app_fast_food.orderitem.dto.OrderItemCreateRequestDTO;
import com.example.app_fast_food.orderitem.entity.OrderItem;
import com.example.app_fast_food.product.ProductRepository;
import com.example.app_fast_food.product.entity.Product;
import com.example.app_fast_food.productdiscount.ProductDiscount;
import com.example.app_fast_food.user.UserRepository;
import com.example.app_fast_food.user.dto.AuthDto;
import com.example.app_fast_food.user.entity.User;
import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final BonusRepository bonusRepository;

    private final OrderMapper mapper;
    private final BonusMapper bonusMapper;

    private final BonusService bonusService;

    public static final String PRODUCT_ENTITY = "Product";
    public static final String BASKET_ENTITY = "Basket";
    public static final String BONUS_ENTITY = "Bonus";

    private static final String USER_BASKET_NOT_FOUND = "Basket with user id `%s` not found";

    @Cacheable(CacheNames.ORDERS)
    public List<OrderResponseDto> getAll() {
        return repository.findAll().stream().map(mapper::toResponseDto).toList();

    }

    public OrderResponseDto getById(@NonNull UUID id) {
        Order order = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Order", id.toString()));
        return mapper.toResponseDto(order);
    }

    @Cacheable(value = CacheNames.ORDERS_BY_STATUS, key = "#p0")
    public List<OrderResponseDto> getOrdersByStatus(String status) {
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

    public ApiMessageResponse emptyBasket(AuthDto auth) {
        Order basket = repository
                .findBasketByUserId(auth.getId())
                .orElseThrow(
                        () -> new UserBasketNotFoundException(
                                USER_BASKET_NOT_FOUND.formatted(auth.getId().toString())));

        basket.getOrderItems().clear();

        basket.setAppliedBonus(false);
        basket.setSelectedBonus(null);

        basket.setDiscountAmount(BigDecimal.ZERO);
        basket.setFinalPrice(BigDecimal.ZERO);
        basket.setTotalPrice(BigDecimal.ZERO);

        repository.save(basket);
        return new ApiMessageResponse("Basket successfully emptied");
    }

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

        OrderItem orderItem = new OrderItem();
        orderItem.setBonus(false);
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(dto.getQuantity());
        orderItem.setUnitPrice(product.getPrice());

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

        orderItem.setQuantity(quantity);

        calculateOrderPrices(order);
        repository.save(order);

        return mapper.toResponseDto(order);
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

    public OrderResponseDto applyBonus(AuthDto auth, ChosenOrderDto dto) {
        Order o = repository.findBasketByUserId(auth.getId())
                .orElseThrow(() -> new UserBasketNotFoundException(
                        USER_BASKET_NOT_FOUND.formatted(auth.getId())));

        Bonus bonus = bonusRepository.findById(dto.getBonusId())
                .orElseThrow(() -> new EntityNotFoundException(BONUS_ENTITY, dto.getBonusId().toString()));

        BonusProductLink bonusProductLink = bonus.getBonusProductLinks()
                .stream().filter(bp -> bp.getId().equals(dto.getBonunProductLinkId()))
                .findFirst().orElseThrow(
                        () -> new EntityNotFoundException("Bonus Product", dto.getBonunProductLinkId().toString()));

        BigDecimal price = bonusProductLink.getProduct().getPrice();
        OrderItem item = new OrderItem();

        item.setBonus(true);
        item.setDiscountAmount(price);
        item.setFinalPrice(BigDecimal.ZERO);
        item.setLineTotal(BigDecimal.ZERO);
        item.setUnitPrice(price);
        item.setOrder(o);

        o.setAppliedBonus(true);
        o.setSelectedBonus(bonus);
        o.getOrderItems().add(item);

        repository.save(o);
        return mapper.toResponseDto(o);
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

    public OrderResponseDto getBasket(AuthDto auth) {
        return mapper.toResponseDto(getOrCreateBasket(auth));
    }

    private BigDecimal applyProductDiscounts(OrderItem orderItem, BigDecimal lineTotal, LocalDate today) {
        Product product = orderItem.getProduct();
        int quantity = orderItem.getQuantity();

        Discount best = product.getDiscounts().stream()
                .map(ProductDiscount::getDiscount)
                .filter(Discount::isActive)
                .filter(d -> !today.isBefore(d.getStartDate()) && !today.isAfter(d.getEndDate()))
                .filter(d -> d.getType() == DiscountType.PRODUCT_QUANTITY)
                .filter(d -> d.getRequiredQuantity() != null && d.getRequiredQuantity() <= quantity)
                .max(Comparator.comparingInt(Discount::getRequiredQuantity))
                .orElse(null);

        if (best == null)
            return BigDecimal.ZERO;

        BigDecimal percent = BigDecimal.valueOf(best.getPercentage())
                .divide(BigDecimal.valueOf(100));

        return lineTotal.multiply(percent);
    }

    private void calculateOrderPrices(Order order) {
        LocalDate today = LocalDate.now();

        BigDecimal total = BigDecimal.ZERO;
        BigDecimal totalDiscount = BigDecimal.ZERO;

        Set<OrderItem> orderItems = order.getOrderItems();

        for (OrderItem oi : orderItems) {
            BigDecimal lineTotal = oi.getProduct().getPrice()
                    .multiply(BigDecimal.valueOf(oi.getQuantity()));

            BigDecimal itemDiscount = applyProductDiscounts(oi, lineTotal, today);

            oi.setLineTotal(lineTotal);
            oi.setDiscountAmount(itemDiscount);
            oi.setFinalPrice(lineTotal.subtract(itemDiscount));

            total = total.add(lineTotal);
            totalDiscount = totalDiscount.add(itemDiscount);
        }

        order.setTotalPrice(total);
        order.setDiscountAmount(totalDiscount);
        order.setFinalPrice(total.subtract(totalDiscount));
    }

    private Order getOrCreateBasket(AuthDto auth) {
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

}
