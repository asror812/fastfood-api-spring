package com.example.app_fast_food.category.dto;

import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponseDto {
    private UUID id;

    private String name;

    private ParentCategoryDto parent;

    private List<SubCategory> subCategories;
}
