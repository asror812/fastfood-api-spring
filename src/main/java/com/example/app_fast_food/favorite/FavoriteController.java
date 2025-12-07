package com.example.app_fast_food.favorite;

import com.example.app_fast_food.common.response.ApiMessageResponse;
import com.example.app_fast_food.product.dto.ProductResponseDto;
import com.example.app_fast_food.user.entity.User;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getFavoriteProducts(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(favoriteService.getFavorites(user.getId()));
    }

    @PostMapping("/{productId}")
    public ResponseEntity<ApiMessageResponse> addToFavorite(@AuthenticationPrincipal User user,
            @PathVariable UUID productId) {
        return ResponseEntity.ok(favoriteService.add(user.getId(), productId));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiMessageResponse> removeFromFavorite(@AuthenticationPrincipal User user,
            @PathVariable UUID productId) {
        return ResponseEntity.ok(favoriteService.remove(user.getId(), productId));
    }

}
