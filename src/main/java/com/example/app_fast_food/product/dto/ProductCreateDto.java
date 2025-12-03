package com.example.app_fast_food.product.dto;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateDto {
    @NotBlank
    private String name;

    @NotNull
    private BigDecimal price;

    @NotNull
    private UUID categoryId;

    private int weight;

    @NotNull
    private AttachmentCreateDto main;

    @NotNull
    private AttachmentCreateDto other;
}
