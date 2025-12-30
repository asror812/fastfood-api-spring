package com.example.app_fast_food.discount.dto;

import lombok.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Getter
@Setter
public class DiscountCreateDto {

    @NotBlank
    private String name;

    @Min(0)
    @Max(100)
    private int percentage;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @Min(1)
    private int requiredQuantity;

    private boolean active;
}
