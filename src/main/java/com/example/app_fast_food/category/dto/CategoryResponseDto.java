package com.example.app_fast_food.category.dto;

import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryResponseDto {
    private UUID id;

    private String name;

    private ParentCategoryDto parent; // safe parent

    private List<SubCategory> subCategories; // safe children
}
