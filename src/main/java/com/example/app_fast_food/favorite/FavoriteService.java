package com.example.app_fast_food.favorite;

import com.example.app_fast_food.common.response.ApiMessageResponse;
import com.example.app_fast_food.exception.EntityNotFoundException;
import com.example.app_fast_food.product.ProductMapper;
import com.example.app_fast_food.product.ProductRepository;
import com.example.app_fast_food.product.dto.ProductResponseDto;
import com.example.app_fast_food.product.entity.Product;
import com.example.app_fast_food.user.CustomerProfileRepository;
import com.example.app_fast_food.user.dto.AuthDto;
import com.example.app_fast_food.user.entity.CustomerProfile;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final CustomerProfileRepository customerProfileRepository;
    private final ProductRepository productRepository;
    private final FavoriteRepository repository;
    private static final String PRODUCT_ENTITY = "Product";
    private static final String CUSTOMER_ENTITY = "Customer";

    private final ProductMapper mapper;

    @Cacheable(value = "favoriteProducts", key = "#p0.id")
    public List<ProductResponseDto> getFavorites(AuthDto auth) {
        List<Favorite> favorites = repository.findAllByUserId(auth.getId());

        return favorites.stream().map(f -> f.getProduct()).map(mapper::toResponseDTO)
                .map(dto -> {
                    dto.setFavorite(true);
                    return dto;
                }).toList();
    }

    @CacheEvict(value = "favoriteProducts", key = "#p0.id")
    @Transactional
    public ApiMessageResponse add(AuthDto auth, UUID productId) {
        if (repository.existsByCustomerIdAndProductId(auth.getId(), productId)) {
            return new ApiMessageResponse("Product already in favorites");
        }

        CustomerProfile profile = customerProfileRepository.findById(auth.getId())
                .orElseThrow(() -> new EntityNotFoundException(CUSTOMER_ENTITY, auth.getId().toString()));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(PRODUCT_ENTITY, productId.toString()));

        Favorite favorite = new Favorite(null, product, profile);
        repository.save(favorite);
        return new ApiMessageResponse("Product added to favorites");
    }

    @CacheEvict(value = "favoriteProducts", key = "#p0.id")
    @Transactional
    public ApiMessageResponse remove(AuthDto auth, UUID productId) {
        repository.deleteByCustomerIdAndProductId(auth.getId(), productId);

        return new ApiMessageResponse("Product removed from favorites");
    }

}
