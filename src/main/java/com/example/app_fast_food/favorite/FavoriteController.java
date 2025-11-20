package com.example.app_fast_food.favorite;

import com.example.app_fast_food.common.response.ApiMessageResponse;
import com.example.app_fast_food.product.dto.ProductResponseDTO;
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
    public ResponseEntity<List<ProductResponseDTO>> getFavoriteProducts(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(favoriteService.getFavorites(user.getId()));
    }

    @PostMapping("/{product_id}")
    public ResponseEntity<ApiMessageResponse> addFavorite(@AuthenticationPrincipal User user,
            @PathVariable UUID product_id) {
        return ResponseEntity.ok(favoriteService.addFavorite(user.getId(), product_id));
    }

}
