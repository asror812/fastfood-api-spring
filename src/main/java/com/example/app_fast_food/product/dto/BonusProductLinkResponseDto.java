package com.example.app_fast_food.product.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BonusProductLinkResponseDto {

    private UUID productId;
    private String productName;
    private int quantity;
}
