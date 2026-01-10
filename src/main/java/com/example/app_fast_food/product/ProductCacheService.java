package com.example.app_fast_food.product;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.app_fast_food.exception.EntityNotFoundException;
import com.example.app_fast_food.product.dto.ProductListResponseDto;
import com.example.app_fast_food.product.dto.ProductResponseDto;
import com.example.app_fast_food.product.entity.Product;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductCacheService {
    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Cacheable("popularProducts")
    public List<ProductResponseDto> getPopularProducts() {
        return repository.getPopularProducts().stream().map(mapper::toResponseDTO).toList();
    }

    @Cacheable("products")
    public List<ProductListResponseDto> getAll() {
        return repository.findAllProductsDetails().stream().map(mapper::toListResponseDto).toList();
    }

    @Cacheable(value = "productsByCategory", key = "#p0")
    public List<ProductResponseDto> getAllByCategoryTree(UUID categoryId) {
        return repository.findProductsByCategoryTree(categoryId)
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Cacheable(value = "campaignProductsBase", key = "#date")
    public List<ProductResponseDto> getCampaignProductsBase(LocalDate date) {
        return repository.getCampaignProducts(date).stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Cacheable(value = "productById", key = "#p0")
    public ProductResponseDto getById(UUID id) {
        Product product = repository.findProductDetailsById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product", id.toString()));

        return mapper.toResponseDTO(product);
    }

}
