package com.example.app_fast_food.category.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryListResponseDto {
    private UUID id;

    private String name;
    private UUID parentId;
}
