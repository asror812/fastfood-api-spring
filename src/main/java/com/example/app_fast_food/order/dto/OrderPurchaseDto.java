package com.example.app_fast_food.order.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class OrderPurchaseDto {
    @NotNull(message = "Order id is required")
    private UUID orderId;

    private UUID bonusId;

    private UUID selectedProductId;
}
