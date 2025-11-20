package com.example.app_fast_food.category;

import com.example.app_fast_food.category.dto.CategoryCreateDto;
import com.example.app_fast_food.category.dto.CategoryListResponseDto;
import com.example.app_fast_food.category.dto.CategoryResponse;
import com.example.app_fast_food.category.dto.CategoryResponseDTO;
import com.example.app_fast_food.category.dto.CategoryResponseDto;
import com.example.app_fast_food.product.ProductService;
import com.example.app_fast_food.product.dto.ProductResponseDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponseDto> create(@RequestBody CategoryCreateDto createDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(createDTO));
    }

    @GetMapping
    public ResponseEntity<List<CategoryListResponseDto>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryResponseDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @GetMapping("/menu/{categoryName}")
    public ResponseEntity<List<ProductResponseDTO>> getAllByCategory(@PathVariable String categoryName) {
        return ResponseEntity.ok(categoryService.getByCategory(categoryName));
    }

}
