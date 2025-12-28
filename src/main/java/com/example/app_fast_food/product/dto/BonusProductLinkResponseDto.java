package com.example.app_fast_food.product.dto;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BonusProductLinkResponseDto implements Serializable {
    private UUID productId;
    private String productName;
    private int quantity;
}
