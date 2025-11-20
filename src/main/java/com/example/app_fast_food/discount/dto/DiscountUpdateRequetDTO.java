package com.example.app_fast_food.discount.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.app_fast_food.product_discounts.ProductDiscount;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DiscountUpdateRequetDTO {
    private UUID id;

    private String name;

    private int percentage;

    private LocalDate startDate;

    private LocalDate endDate;

    private int requiredQuantity;

    private boolean isActive;

    private List<ProductDiscount> products = new ArrayList<>();
}
