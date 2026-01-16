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
    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Price is required")
    private BigDecimal price;

    private int weight;

    @NotNull(message = "Category id is required")
    private UUID categoryId;

    @NotNull(message = "Images are required")
    private List<ProductImageDto> images;
}
