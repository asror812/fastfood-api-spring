package com.example.app_fast_food.product_discounts.dto;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ProductDiscountResponseDto {
    private UUID id;
    private UUID productId;

    private UUID discountId;
    private String name;
    private int percentage;
    private LocalDate startDate;
    private LocalDate endDate;
    private int requiredQuantity;

    private boolean isActive;

}
