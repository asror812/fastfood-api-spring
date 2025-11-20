package com.example.app_fast_food.product.dto;

import java.math.BigDecimal;

import com.example.app_fast_food.attachment.entity.Attachment;
import com.example.app_fast_food.category.entity.Category;

import jakarta.validation.constraints.Min;
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
public class ProductCreateRequestDTO {
    @NotBlank
    private String name;

    @NotNull
    private BigDecimal price;

    @NotBlank
    private Category category;

    @Min(1)
    private int weight;

    @NotNull
    private Attachment main;

    @NotNull
    private Attachment other;
}
