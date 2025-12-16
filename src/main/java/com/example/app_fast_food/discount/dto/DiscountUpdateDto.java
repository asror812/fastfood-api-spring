package com.example.app_fast_food.discount.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountUpdateDto {

    @NotBlank
    private String name;

    private int percentage;

    private LocalDate startDate;

    private LocalDate endDate;

    private int requiredQuantity;

    private boolean isActive;
}
