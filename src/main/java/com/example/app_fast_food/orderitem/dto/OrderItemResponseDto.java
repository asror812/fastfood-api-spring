package com.example.app_fast_food.orderitem.dto;

import java.io.Serializable;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemResponseDto implements Serializable{
    private UUID id;

    private int quantity;

    private UUID productId;

    private UUID orderId;
}
