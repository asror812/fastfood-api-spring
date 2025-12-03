package com.example.app_fast_food.product;

import com.example.app_fast_food.exceptions.EntityNotFoundException;
import com.example.app_fast_food.product.dto.ProductCreateDto;
import com.example.app_fast_food.product.dto.ProductResponseDto;
import com.example.app_fast_food.product.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductMapper mapper;

    private final ProductRepository repository;

    protected ProductResponseDto create(ProductCreateDto productCreateDTO) {
        Product product = mapper.toEntity(productCreateDTO);
        repository.save(product);

        return mapper.toResponseDTO(product);
    }

    public List<ProductResponseDto> getAllByCategory(UUID categoryId) {
        log.error("Category id: " + categoryId);
        return getProductResponseDTOS(repository.findProductsByCategoryId(categoryId));
    }

    public List<ProductResponseDto> getCampaignProducts() {
        return getProductResponseDTOS(repository.getCampaignProducts());
    }

    public ProductResponseDto getSpecificProduct(UUID id) {
        Product product = repository.findProductById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product", id.toString()));

        return mapper.toResponseDTO(product);
    }

    public List<ProductResponseDto> getProductResponseDTOS(List<Product> products) {
        return products.stream().map(p -> mapper.toResponseDTO(p)).toList();
    }

    public List<ProductResponseDto> getPopularProducts() {
        return repository.getPopularProducts().stream().map(p -> mapper.toResponseDTO(p)).toList();
    }

    public List<ProductResponseDto> getAll() {
        return repository.findAll().stream().map(p -> mapper.toResponseDTO(p)).toList();
    }
}