package com.example.app_fast_food.category;

import com.example.app_fast_food.category.dto.CategoryCreateDto;
import com.example.app_fast_food.category.dto.CategoryResponseDTO;
import com.example.app_fast_food.category.dto.CategoryUpdateDto;
import com.example.app_fast_food.category.entity.Category;
import com.example.app_fast_food.common.mapper.BaseMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper
        extends BaseMapper<Category, CategoryResponseDTO> {

    @Override
    public CategoryResponseDTO toResponseDTO(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    public void update(CategoryUpdateDto categoryUpdateDTO, @MappingTarget Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    public Category toEntity(CategoryCreateDto createDTO);
}
