package com.example.app_fast_food.orderitem.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class OrderItemCreateRequestDTO {
    @NotNull
    private UUID productId;

    @Min(1)
    private int quantity;
}
