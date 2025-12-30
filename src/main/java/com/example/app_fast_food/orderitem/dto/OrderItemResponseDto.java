package com.example.app_fast_food.orderitem.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemResponseDto implements Serializable {
    private UUID id;

    private int quantity;

    private BigDecimal unitPrice;

    private ProductDto product;

    private BigDecimal lineTotal;

    private BigDecimal discountAmount;

    private BigDecimal finalPrice;
}
