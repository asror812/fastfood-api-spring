package com.example.app_fast_food.check.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CheckCreateRequestDTO {

    @NotNull
    private UUID orderId;

    @NotNull
    private UUID userId;

    @NotNull
    private Long totalAmount;

    @NotNull
    private Long totalDiscount;

    @NotNull
    private Long totalPrice;

    @NotNull
    private Double longitude;

    @NotNull
    private Double latitude;
}
