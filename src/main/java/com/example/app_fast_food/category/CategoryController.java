package com.example.app_fast_food.category;

import com.example.app_fast_food.category.dto.CategoryCreateDto;
import com.example.app_fast_food.category.dto.CategoryResponseDto;
import com.example.app_fast_food.product.ProductService;
import com.example.app_fast_food.product.dto.ProductResponseDto;

import jakarta.validation.Valid;
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
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<CategoryResponseDto> create(@Valid @RequestBody CategoryCreateDto createDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(createDTO));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/parent")
    public ResponseEntity<List<CategoryResponseDto>> getAllParentCategories() {
        return ResponseEntity.ok(categoryService.getParentCategories());
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryResponseDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @GetMapping("/menu/{id}")
    public ResponseEntity<List<ProductResponseDto>> getProductsByCategoryName(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.getAllByCategory(id));
    }

}
