package com.example.app_fast_food.discount.dto;

import lombok.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountCreateDto {

    @NotBlank
    private String name;

    @Min(1)
    private int percentage;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @Min(1)
    private int requiredQuantity;

    private boolean isActive;
}
