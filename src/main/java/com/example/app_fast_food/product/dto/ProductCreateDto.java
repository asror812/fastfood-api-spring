package com.example.app_fast_food.product.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCreateDto {
    @NotBlank
    private String name;

    @NotNull
    private BigDecimal price;

    private int weight;

    @NotNull
    private UUID categoryId;

    @NotNull
    private List<ProductImageDto> images;
}
