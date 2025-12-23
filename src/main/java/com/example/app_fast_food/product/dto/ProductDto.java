package com.example.app_fast_food.product.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.example.app_fast_food.bonus.dto.bonus.CategoryDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private UUID id;

    private String name;

    private BigDecimal price;

    private CategoryDto category;

    private int weight;
}
