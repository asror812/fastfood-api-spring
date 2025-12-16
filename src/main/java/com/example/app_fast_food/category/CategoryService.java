package com.example.app_fast_food.category;

import com.example.app_fast_food.category.dto.CategoryCreateDto;
import com.example.app_fast_food.category.dto.CategoryResponseDto;
import com.example.app_fast_food.category.entity.Category;
import com.example.app_fast_food.exception.AlreadyExistsException;
import com.example.app_fast_food.exception.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;
    private static final String CATEGORY_ENTITY = "Category";

    public CategoryResponseDto create(CategoryCreateDto createDto) {
        UUID parentId = createDto.getParentId();
        String name = createDto.getName();

        Category parent = null;

        if (parentId != null) {
            parent = repository.findById(parentId)
                    .orElseThrow(() -> new EntityNotFoundException(CATEGORY_ENTITY, parentId.toString()));
        }

        repository.findByName(name)
                .ifPresent(c -> {
                    throw new AlreadyExistsException(CATEGORY_ENTITY, "name", name);
                });

        Category category = new Category();
        category.setParent(parent);
        category.setName(name);

        repository.save(category);
        return mapper.toResponseDto(category);
    }

    public List<CategoryResponseDto> getAll() {
        return repository.findAll().stream().map(mapper::toResponseDto).toList();
    }

    public CategoryResponseDto findById(UUID id) {
        return repository.findById(id).map(mapper::toResponseDto)
                .orElseThrow(() -> new EntityNotFoundException(CATEGORY_ENTITY, id.toString()));
    }

    public List<CategoryResponseDto> getParentCategories() {
        List<Category> parentCategories = repository.getParentCategories();

        return parentCategories.stream().map(mapper::toResponseDto).toList();
    }

}
