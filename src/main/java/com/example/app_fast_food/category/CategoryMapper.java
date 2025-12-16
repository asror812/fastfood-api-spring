package com.example.app_fast_food.category;

import com.example.app_fast_food.category.dto.CategoryCreateDto;
import com.example.app_fast_food.category.dto.CategoryResponseDto;
import com.example.app_fast_food.category.dto.CategoryUpdateDto;
import com.example.app_fast_food.category.dto.SubCategory;
import com.example.app_fast_food.category.entity.Category;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    public CategoryResponseDto toResponseDto(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "subCategories", ignore = true)
    public void update(CategoryUpdateDto categoryUpdateDTO, @MappingTarget Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "subCategories", ignore = true)
    @Mapping(target = "parent", ignore = true)
    public Category toEntity(CategoryCreateDto createDTO);

    default List<SubCategory> mapCategories(List<Category> subCategories) {
        return subCategories.stream().map(c -> new SubCategory(c.getId(), c.getName())).toList();
    }
}
