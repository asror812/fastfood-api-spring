package com.example.app_fast_food.discount.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.example.app_fast_food.product_discounts.ProductDiscount;

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

    private int percentage;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(name = "required_quantity")
    private int requiredQuantity;

    @Column(name = "is_active")
    private boolean isActive;

    @OneToMany(mappedBy = "discount")
    private List<ProductDiscount> products = new ArrayList<>();

    public Discount(UUID id, String name, int percentage, LocalDate startDate, LocalDate endDate,
            int requiredQuantity, boolean isActive) {
        this.id = id;
        this.name = name;
        this.percentage = percentage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.requiredQuantity = requiredQuantity;
        this.isActive = isActive;
    }

}
