package com.example.app_fast_food.category.dto;

import java.util.List;
import java.util.UUID;

public record CategoryResponseDto(
        UUID id,
        String name,
        UUID parentId,
        List<SubCategoryDTO> subCategories,
        List<ProductDTO> products) {
}

record SubCategoryDTO(
        UUID id,
        String name) {
}

record ProductDTO(
        UUID id,
        String name,
        Double price) {
}
