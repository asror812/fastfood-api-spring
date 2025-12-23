package com.example.app_fast_food.product.dto;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDiscountResponseDto {
    private UUID id;
    private String name;
    private int percentage;

    private LocalDate startDate;
    private LocalDate endDate;
    private int requiredQuantity;
}
