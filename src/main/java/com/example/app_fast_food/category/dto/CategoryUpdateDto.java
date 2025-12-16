package com.example.app_fast_food.category.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryUpdateDto {
    @NotBlank
    private String name;

    @NotNull
    private UUID parentId;
}
