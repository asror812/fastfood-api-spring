package com.example.app_fast_food.orderitem.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderItemUpdateRequestDTO {
    @Min(1)
    private int quantity;

    @NotNull
    private UUID productId;
}
