package com.example.app_fast_food.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderPurchaseDTO {

    private UUID orderId;
    private UUID bonusId;
    private UUID selectedProductId;
}
