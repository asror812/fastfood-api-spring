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
        private final ProductMapper mapper;

        @Cacheable(value = "favoriteProducts", key = "#p0.id")
        public List<ProductResponseDto> getFavorites(AuthDto auth) {
                CustomerProfile customerProfile = customerProfileRepository
                                .findById(auth.getId())
                                .orElseThrow(() -> new EntityNotFoundException("Customer", auth.getId().toString()));

                return customerProfile.getFavouriteProducts().stream().map(mapper::toResponseDTO).toList();
        }

        @CacheEvict(value = "favoriteProducts", key = "#p0.id")
        @Transactional
        public ApiMessageResponse add(AuthDto auth, UUID productId) {
                CustomerProfile profile = customerProfileRepository.findById(auth.getId())
                                .orElseThrow(() -> new EntityNotFoundException("Customer", auth.getId().toString()));

                boolean alreadyExists = profile.getFavouriteProducts().stream()
                                .anyMatch(p -> p.getId().equals(profile.getId()));

                if (alreadyExists) {
                        return new ApiMessageResponse("Product already in favorites");
                }

                Product product = productRepository.findById(productId)
                                .orElseThrow(() -> new EntityNotFoundException("Product", productId.toString()));
                profile.getFavouriteProducts().add(product);

                return new ApiMessageResponse("Product added to favorites");
        }

        @CacheEvict(value = "favoriteProducts", key = "#p0.id")
        @Transactional
        public ApiMessageResponse remove(AuthDto auth, UUID productId) {
                CustomerProfile profile = customerProfileRepository.findById(auth.getId())
                                .orElseThrow(() -> new EntityNotFoundException("User", auth.getId().toString()));

                boolean removed = profile.getFavouriteProducts()
                                .removeIf(p -> p.getId().equals(productId));

                return removed
                                ? new ApiMessageResponse("Product removed from favorites")
                                : new ApiMessageResponse("Product not in favorites");
        }

}
