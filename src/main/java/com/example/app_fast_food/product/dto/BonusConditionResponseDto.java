package com.example.app_fast_food.product.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BonusConditionResponseDto {
     private UUID id;

    private String type;

    private String value;
}
