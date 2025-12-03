package com.example.app_fast_food.order;

import com.example.app_fast_food.bonus.dto.bonus.BonusResponseDto;
import com.example.app_fast_food.check.CheckRepository;
import com.example.app_fast_food.check.entity.Check;
import com.example.app_fast_food.common.response.ApiMessageResponse;
import com.example.app_fast_food.discount.entity.Discount;
import com.example.app_fast_food.exceptions.EntityNotFoundException;
import com.example.app_fast_food.exceptions.TooFarException;
import com.example.app_fast_food.filial.FilialService;
import com.example.app_fast_food.filial.entity.NearestFilial;
import com.example.app_fast_food.order.dto.OrderResponseDto;
import com.example.app_fast_food.order.entity.Order;
import com.example.app_fast_food.order.entity.OrderStatus;
import com.example.app_fast_food.orderItem.OrderItemRepository;
import com.example.app_fast_food.orderItem.dto.OrderItemCreateRequestDTO;
import com.example.app_fast_food.orderItem.entity.OrderItem;
import com.example.app_fast_food.product.ProductMapper;
import com.example.app_fast_food.product.ProductRepository;
import com.example.app_fast_food.product.dto.ProductResponseDto;
import com.example.app_fast_food.product.entity.Product;
import com.example.app_fast_food.user.entity.User;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final CheckRepository checkRepository;

    private final FilialService filialService;

    private final OrderMapper mapper;
    private final ProductMapper productMapper;

    public OrderResponseDto addProduct(OrderItemCreateRequestDTO dto, User user) {
        Order order = repository.findBasketByUserId(user.getId())
                .orElseGet(() -> {
                    Order newBasket = new Order();
                    newBasket.setUser(user);
                    newBasket.setOrderStatus(OrderStatus.BASKET);
                    return repository.save(newBasket);
                });

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Product", dto.getProductId().toString()));

        Optional<OrderItem> existing = order.getOrderItems().stream()
                .filter(oi -> oi.getProduct().getId().equals(dto.getProductId()))
                .findFirst();

        if (existing.isPresent()) {
            throw new RuntimeException("Product already added to basket");
        }

        OrderItem orderItem = new OrderItem(null, 1, product, order);
        orderItemRepository.save(orderItem);

        calculateOrderPrices(order, user);

        repository.save(order);
        return mapper.toResponseDto(order);
    }

    public OrderResponseDto updateQuantity(User user, UUID productId, int quantity) {
        Order order = repository.findBasketByUserId(user.getId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Basket",
                                user.getId().toString()));

        OrderItem orderItem = order.getOrderItems().stream()
                .filter(o -> o.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(
                        () -> new EntityNotFoundException("Product", productId.toString()));

        orderItem.setQuantity(quantity);
        orderItemRepository.save(orderItem);

        // todo
        calculateOrderPrices(order, user);

        repository.save(order);
        return mapper.toResponseDto(order);
    }

    // to do
    private void calculateOrderPrices(Order order, User user) {

    }

    public OrderResponseDto getBasket(User user) {
        Order order = repository.findBasketByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Basket",
                        "of user " + user.getId()));

        return mapper.toResponseDto(order);
    }

    public List<OrderResponseDto> getByOrderStatus(String status) {
        List<Order> orders = repository.findByOrderStatus(OrderStatus.valueOf(status));

        return orders.stream()
                .map(mapper::toResponseDto)
                .toList();
    }

    public ApiMessageResponse deleteBasket(UUID id) {
        repository.deleteOrderByUserIdAndOrderStatus(id, OrderStatus.BASKET);
        return new ApiMessageResponse("Basket successfully deleted");
    }

    public void confirmOrder(User user) {
        UUID userId = user.getId();

        Order order = repository
                .findBasketByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Basket",
                        userId.toString()));

        order.setOrderStatus(OrderStatus.IN_PROCESS);
        repository.save(order);

        // todo
        NearestFilial nearestOne = filialService.findTheNearestOne(10d, 10d);
        if (nearestOne.getDistance() >= 5) {
            throw new TooFarException("We cannot deliver to your location");
        }

        Check check = new Check(null, order, user, nearestOne, "John");

        checkRepository.save(check);
    }

    public OrderResponseDto removeProduct(User user, UUID productId) {
        Order order = repository.findBasketByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Basket",
                        "of user " + user.getId()));

        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Order item",
                        productId.toString()));

        order.getOrderItems().removeIf(oi -> oi.getProduct().equals(product));

        calculateOrderPrices(order, user);
        repository.save(order);

        return mapper.toResponseDto(order);
    }

    public ProductResponseDto selectBonus(User user, UUID bonusId) {
        Order o = repository.findBasketByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Basket",
                        "of user " + user.getId()));

        Product p = productRepository.findProductById(bonusId)
                .orElseThrow(() -> new EntityNotFoundException("Product",
                        bonusId.toString()));

        OrderItem item = new OrderItem(null, 1, p, o);

        o.getOrderItems().add(item);
        repository.save(o);

        return productMapper.toResponseDTO(p);
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

    public List<BonusResponseDto> getAvailableBonuses(User user) {
        // TODO:
        throw new UnsupportedOperationException("Unimplemented method 'getAvailableBonuses'");
    }

    public List<OrderResponseDto> getAll() {
        return repository.findAll().stream().map(o -> mapper.toResponseDto(o)).toList();

    }

    public OrderResponseDto getById(UUID id) {
        Order order = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order", id.toString()));

        return mapper.toResponseDto(order);
    }

}
