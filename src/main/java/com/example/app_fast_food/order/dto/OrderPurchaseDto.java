package com.example.app_fast_food.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class OrderPurchaseDto {

    private UUID orderId;
    private UUID bonusId;
    private UUID selectedProductId;
}
