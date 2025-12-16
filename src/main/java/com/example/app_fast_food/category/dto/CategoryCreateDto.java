package com.example.app_fast_food.category.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryCreateDto {
    @NotBlank
    private String name;

    private UUID parentId;
}
