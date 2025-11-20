package com.example.app_fast_food.discount.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountResponseDTO {
    private UUID id;

    private String name;

    private int percentage;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer requiredQuantity;

    private boolean isActive;
}
