package com.example.app_fast_food.category.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ParentCategoryDto {
    private UUID id;
    private String name;
}
