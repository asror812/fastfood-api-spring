package com.example.app_fast_food.favorite;

import com.example.app_fast_food.common.response.ApiMessageResponse;
import com.example.app_fast_food.exception.EntityNotFoundException;
import com.example.app_fast_food.product.ProductRepository;
import com.example.app_fast_food.product.ProductService;
import com.example.app_fast_food.product.dto.ProductResponseDto;
import com.example.app_fast_food.product.entity.Product;
import com.example.app_fast_food.user.CustomerProfileRepository;
import com.example.app_fast_food.user.entity.CustomerProfile;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final CustomerProfileRepository customerProfileRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;

    public List<ProductResponseDto> getFavorites(UUID userId) {
        CustomerProfile profile = customerProfileRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User with `%s` not found".formatted(userId)));

        return productService
                .getProductResponseDTOS(profile.getFavouriteProducts().stream().toList());
    }

    public ApiMessageResponse add(UUID userId, UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Product", productId.toString()));

        CustomerProfile profile = customerProfileRepository.findWithFavouriteProductsByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Customer", userId.toString()));

        boolean exists = profile.getFavouriteProducts().stream()
                .anyMatch(p -> p.getId().equals(productId));

        if (!exists) {
            profile.getFavouriteProducts().add(product);
            customerProfileRepository.save(profile);
        }

        return new ApiMessageResponse("Product added to favorites");
    }

    public ApiMessageResponse remove(UUID userId, UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Product", productId.toString()));

        CustomerProfile profile = customerProfileRepository.findWithFavouriteProductsByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("User", userId.toString()));

        profile.getFavouriteProducts().remove(product);
        customerProfileRepository.save(profile);

        return new ApiMessageResponse("Product removed from favorites");
    }

}
