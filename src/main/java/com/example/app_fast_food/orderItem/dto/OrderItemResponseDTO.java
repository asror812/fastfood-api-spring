package com.example.app_fast_food.orderItem.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderItemResponseDTO {
    private UUID id;

    private int quantity;

    private UUID productId;

    private UUID orderId;
}
