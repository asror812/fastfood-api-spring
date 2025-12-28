package com.example.app_fast_food.product;

import com.example.app_fast_food.exception.EntityNotFoundException;
import com.example.app_fast_food.product.dto.ProductCreateDto;
import com.example.app_fast_food.product.dto.ProductResponseDto;
import com.example.app_fast_food.product.entity.Product;
import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper mapper;

    private final ProductRepository repository;

    @CacheEvict(value = { "campaignProducts", "popularProducts", "productsByCategory" }, allEntries = true)
    @Transactional
    public ProductResponseDto create(ProductCreateDto createDto) {
        Product product = mapper.toEntity(createDto);
        repository.save(product);
        return mapper.toResponseDTO(product);
    }

    @Cacheable(value = "productsByCategory", key = "#p0")
    public List<ProductResponseDto> getAllByCategory(UUID categoryId) {
        return getProductResponseDTOS(
                repository.findProductsByCategoryTree(categoryId));
    }

    @Cacheable("campaignProducts")
    public List<ProductResponseDto> getCampaignProducts() {
        return getProductResponseDTOS(repository.getCampaignProducts());
    }

    @Cacheable(value = "productById", key = "#p0")
    public ProductResponseDto getSpecificProduct(UUID id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product", id.toString()));

        return mapper.toResponseDTO(product);
    }

    public List<ProductResponseDto> getProductResponseDTOS(List<Product> products) {
        return products.stream().map(mapper::toResponseDTO).toList();
    }

    @Cacheable("popularProducts")
    public List<ProductResponseDto> getPopularProducts() {
        return repository.getPopularProducts().stream().map(mapper::toResponseDTO).toList();
    }

    @Cacheable("products")
    public List<ProductResponseDto> getAll() {
        return repository.findAll().stream().map(mapper::toResponseDTO).toList();
    }
}