package com.example.app_fast_food.discount.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.app_fast_food.productdiscount.ProductDiscount;

@Entity
@Table(name = "discounts")
@NoArgsConstructor
@Getter
@Setter
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private DiscountType type;

    @Column(name = "percentage")
    private int percentage;

    @Column(name = "required_quantity")
    private Integer requiredQuantity;

    @Column(name = "required_amount")
    private BigDecimal requiredAmount;

    @Column(nullable = false, name = "start_date")
    private LocalDate startDate;

    @Column(nullable = false, name = "end_date")
    private LocalDate endDate;

    @Column(name = "is_active")
    private boolean active;

    @OneToMany(mappedBy = "discount")
    private List<ProductDiscount> products = new ArrayList<>();

    public Discount(String name, DiscountType type, int precentage, Integer requiredQuantity, BigDecimal requiredAmount,
            LocalDate startDate, LocalDate endDate, boolean active) {
        this.name = name;
        this.type = type;
        this.percentage = precentage;
        this.requiredQuantity = requiredQuantity;
        this.requiredAmount = requiredAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = active;
    }

}
