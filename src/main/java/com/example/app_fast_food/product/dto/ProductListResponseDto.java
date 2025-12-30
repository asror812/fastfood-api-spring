package com.example.app_fast_food.product.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class ProductListResponseDto implements Serializable {
    private UUID id;
    private String name;
    private BigDecimal price;
    private CategoryResponseDto category;
    private int weight;

    private Set<ProductImageResponseDto> images = new HashSet<>();
}
