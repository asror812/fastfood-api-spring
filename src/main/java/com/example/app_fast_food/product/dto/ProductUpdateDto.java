package com.example.app_fast_food.product.dto;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductUpdateDto {
    private String name;
    private BigDecimal price;

    private UUID categoryId;
    private Integer weight;

    private Set<ProductImageDto> images;
}