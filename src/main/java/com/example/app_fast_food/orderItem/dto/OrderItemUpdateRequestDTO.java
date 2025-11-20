package com.example.app_fast_food.orderItem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderItemUpdateRequestDTO {

    private int quantity;

    @NotNull
    private UUID productId;
}
