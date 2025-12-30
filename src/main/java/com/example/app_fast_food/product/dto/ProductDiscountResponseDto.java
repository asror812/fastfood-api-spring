package com.example.app_fast_food.product.dto;

import java.io.Serializable;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDiscountResponseDto implements Serializable {
    private UUID id;
    private String name;
    private int percentage;

    private Integer requiredQuantity;
}
