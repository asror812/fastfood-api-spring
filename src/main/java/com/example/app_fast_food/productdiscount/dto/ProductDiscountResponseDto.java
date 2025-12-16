package com.example.app_fast_food.productdiscount.dto;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
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
