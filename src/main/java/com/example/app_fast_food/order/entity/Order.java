package com.example.app_fast_food.order.entity;

import com.example.app_fast_food.bonus.entity.Bonus;
import com.example.app_fast_food.discount.entity.Discount;
import com.example.app_fast_food.orderitem.entity.OrderItem;
import com.example.app_fast_food.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<OrderItem> orderItems = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private PaymentType paymentType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "order_discounts", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "discount_id"))
    private List<Discount> discounts = new ArrayList<>();

    @ManyToOne()
    @JoinColumn(name = "applied_bonus")
    private Bonus appliedBonus;

    private LocalDateTime createdAt;

    private BigDecimal totalPrice;
    private BigDecimal discountAmount;
    private BigDecimal finalPrice;

    public Order(UUID id, OrderStatus orderStatus,
            PaymentType paymentType, User user) {
        this.id = id;
        this.orderStatus = orderStatus;
        this.paymentType = paymentType;
        this.user = user;
    }

}
