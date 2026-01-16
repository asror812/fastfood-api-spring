package com.example.app_fast_food.orderitem.entity;

import com.example.app_fast_food.order.entity.Order;
import com.example.app_fast_food.product.entity.Product;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "order_items")
@NoArgsConstructor
@Setter
@Getter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    private int quantity;

    @Column(name = "line_total")
    private BigDecimal lineTotal;

    @Column(name = "discount_amount")
    private BigDecimal discountAmount;

    @Column(name = "final_price")
    private BigDecimal finalPrice;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "is_bonus")
    private boolean bonus;

    public OrderItem(BigDecimal unitPrice, int quantity, BigDecimal totalPrice, BigDecimal discountAmount,
            BigDecimal finalPrice, Product product, Order order) {
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.lineTotal = totalPrice;
        this.discountAmount = discountAmount;
        this.finalPrice = finalPrice;
        this.product = product;
        this.order = order;
    }

}
