package com.example.app_fast_food.category;

import com.example.app_fast_food.category.dto.CategoryCreateDto;
import com.example.app_fast_food.category.dto.CategoryResponseDTO;
import com.example.app_fast_food.category.dto.CategoryResponseDto;
import com.example.app_fast_food.category.entity.Category;
import com.example.app_fast_food.common.mapper.BaseMapper;
import com.example.app_fast_food.common.service.GenericService;
import com.example.app_fast_food.product.dto.ProductResponseDTO;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
@Getter
public class CategoryService
        extends GenericService<Category, CategoryResponseDTO> {

    private final CategoryRepository repository;
    private final Class<Category> entityClass = Category.class;
    private CategoryMapper mapper;

    public CategoryService(BaseMapper<Category, CategoryResponseDTO> mapper, CategoryRepository repository,
            CategoryMapper mapper2) {
        super(mapper);
        this.repository = repository;
        mapper = mapper2;
    }

    public CategoryResponseDto create(CategoryCreateDto createDTO) {
        mapper.toEntity(createDTO);
        return null;
    }

    public List<ProductResponseDTO> getByCategory(String categoryName) {
        return Collections.emptyList();
    }

}
