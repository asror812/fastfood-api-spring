package com.example.app_fast_food.order;

import com.example.app_fast_food.bonus.BonusMapper;
import com.example.app_fast_food.bonus.BonusRepository;
import com.example.app_fast_food.bonus.BonusService;
import com.example.app_fast_food.bonus.dto.bonus.BonusResponseDTO;
import com.example.app_fast_food.check.CheckRepository;
import com.example.app_fast_food.check.dto.CheckCreateRequestDTO;
import com.example.app_fast_food.check.entity.Check;
import com.example.app_fast_food.common.mapper.BaseMapper;
import com.example.app_fast_food.common.response.ApiMessageResponse;
import com.example.app_fast_food.common.service.GenericService;
import com.example.app_fast_food.discount.DiscountService;
import com.example.app_fast_food.discount.entity.Discount;
import com.example.app_fast_food.exceptions.EntityNotFoundException;
import com.example.app_fast_food.exceptions.TooFarException;
import com.example.app_fast_food.filial.FilialRepository;
import com.example.app_fast_food.filial.FilialService;
import com.example.app_fast_food.filial.entity.NearestFilial;
import com.example.app_fast_food.order.dto.OrderResponseDTO;
import com.example.app_fast_food.order.entity.Order;
import com.example.app_fast_food.order.entity.OrderStatus;
import com.example.app_fast_food.orderItem.OrderItemRepository;
import com.example.app_fast_food.orderItem.OrderItemService;
import com.example.app_fast_food.orderItem.dto.OrderItemCreateRequestDTO;
import com.example.app_fast_food.orderItem.entity.OrderItem;
import com.example.app_fast_food.product.ProductMapper;
import com.example.app_fast_food.product.ProductRepository;
import com.example.app_fast_food.product.dto.ProductResponseDTO;
import com.example.app_fast_food.product.entity.Product;
import com.example.app_fast_food.user.UserRepository;
import com.example.app_fast_food.user.entity.User;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@Getter
public class OrderService extends GenericService<Order, OrderResponseDTO> {
    private final Class<Order> entityClass = Order.class;

    private final OrderRepository repository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final CheckRepository checkRepository;
    private final BonusRepository bonusRepository;
    private final FilialRepository filialRepository;

    private final FilialService filialService;
    private final OrderItemService orderItemService;
    private final BonusService bonusService;
    private final DiscountService discountService;

    private final OrderMapper mapper;
    private final BonusMapper bonusMapper;
    private final ProductMapper productMapper;

    public OrderService(BaseMapper<Order, OrderResponseDTO> baseMapper, OrderRepository repository,
            UserRepository userRepository, OrderItemRepository orderItemRepository,
            ProductRepository productRepository,
            CheckRepository checkRepository, BonusRepository bonusRepository, ProductMapper productMapper,
            FilialRepository filialRepository, FilialService filialService,
            OrderItemService orderItemService,
            BonusService bonusService, DiscountService discountService, OrderMapper mapper,
            BonusMapper bonusMapper) {
        super(baseMapper);

        this.repository = repository;
        this.userRepository = userRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.checkRepository = checkRepository;
        this.bonusRepository = bonusRepository;
        this.productMapper = productMapper;
        this.filialRepository = filialRepository;
        this.filialService = filialService;
        this.orderItemService = orderItemService;
        this.bonusService = bonusService;
        this.discountService = discountService;
        this.mapper = mapper;
        this.bonusMapper = bonusMapper;
    }

    public OrderResponseDTO addProduct(OrderItemCreateRequestDTO dto, User user) {
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
        return mapper.toResponseDTO(order);
    }

    public OrderResponseDTO updateQuantity(User user, UUID productId, int quantity) {
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
        return mapper.toResponseDTO(order);
    }

    // to do
    private void calculateOrderPrices(Order order, User user) {

    }

    public OrderResponseDTO getBasket(User user) {
        Order order = repository.findBasketByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Basket",
                        "of user " + user.getId()));

        return mapper.toResponseDTO(order);
    }

    public List<OrderResponseDTO> getByOrderStatus(String status) {
        List<Order> orders = repository.findByOrderStatus(OrderStatus.valueOf(status));

        return orders.stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public ApiMessageResponse deleteBasket(UUID id) {
        repository.deleteOrderByUserIdAndOrderStatus(id, OrderStatus.BASKET);
        return new ApiMessageResponse("Basket successfully deleted");
    }

    public void confirmOrder(CheckCreateRequestDTO dto, User user) {
        Order order = repository
                .findBasketByUserId(dto.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Basket",
                        dto.getOrderId().toString()));

        order.setOrderStatus(OrderStatus.IN_PROCESS);
        repository.save(order);

        NearestFilial nearestOne = filialService.findTheNearestOne(dto.getLongitude(), dto.getLatitude());
        if (nearestOne.getDistance() >= 5) {
            throw new TooFarException("We cannot deliver to your location");
        }

        Check check = new Check(null, order, user, nearestOne, "John");

        checkRepository.save(check);
    }

    public OrderResponseDTO removeProduct(User user, UUID productId) {
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

        return mapper.toResponseDTO(order);
    }

    public ProductResponseDTO selectBonus(User user, UUID bonusId) {
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

    public List<BonusResponseDTO> getAvailableBonuses(User user) {
        // TODO:
        throw new UnsupportedOperationException("Unimplemented method 'getAvailableBonuses'");
    }

}
