package com.example.app_fast_food.product;

import com.example.app_fast_food.product.dto.ProductResponseDto;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/popular")
    public ResponseEntity<List<ProductResponseDto>> getPopularProducts() {
        return ResponseEntity.ok(productService.getPopularProducts());
    }

    @GetMapping("/campaign")
    public ResponseEntity<List<ProductResponseDto>> getCampaignProducts() {
        return ResponseEntity.ok(productService.getCampaignProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.getSpecificProduct(id));
    }

}
