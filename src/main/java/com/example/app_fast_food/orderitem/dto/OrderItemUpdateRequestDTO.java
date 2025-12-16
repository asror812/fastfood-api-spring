package com.example.app_fast_food.orderitem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderItemUpdateRequestDTO {

    private int quantity;

    @NotNull
    private UUID productId;
}
