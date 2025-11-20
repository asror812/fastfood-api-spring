package com.example.app_fast_food.check.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CheckUpdateRequestDTO {

    @NotNull
    private BigDecimal totalAmount;

    @NotNull
    private BigDecimal totalDiscount;

    @NotNull
    private BigDecimal totalPrice;

    @NotBlank
    private String courier;
}
