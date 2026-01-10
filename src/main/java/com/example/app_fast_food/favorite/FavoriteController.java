package com.example.app_fast_food.favorite;

import com.example.app_fast_food.common.response.ApiMessageResponse;
import com.example.app_fast_food.product.dto.ProductResponseDto;
import com.example.app_fast_food.user.dto.AuthDto;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getFavoriteProducts(@AuthenticationPrincipal AuthDto auth) {
        return ResponseEntity.ok(favoriteService.getFavorites(auth));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{productId}")
    public ResponseEntity<ApiMessageResponse> addToFavorite(@AuthenticationPrincipal AuthDto auth,
            @PathVariable("productId") UUID productId) {
        return ResponseEntity.ok(favoriteService.add(auth, productId));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiMessageResponse> removeFromFavorite(@AuthenticationPrincipal AuthDto auth,
            @PathVariable("productId") UUID productId) {
        return ResponseEntity.ok(favoriteService.remove(auth, productId));
    }

}
