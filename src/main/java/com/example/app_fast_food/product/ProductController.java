package com.example.app_fast_food.product;

import com.example.app_fast_food.attachment.AttachmentService;
import com.example.app_fast_food.attachment.dto.AttachmentResponseDto;
import com.example.app_fast_food.product.dto.ProductListResponseDto;
import com.example.app_fast_food.product.dto.ProductResponseDto;
import com.example.app_fast_food.user.dto.AuthDto;
import com.example.app_fast_food.user.entity.User;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductCacheService cacheService;
    private final AttachmentService attachmentService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProductListResponseDto>> getAll() {
        return ResponseEntity.ok(cacheService.getAll());
    }

    @GetMapping("/popular")
    public ResponseEntity<List<ProductResponseDto>> getPopularProducts() {
        return ResponseEntity.ok(cacheService.getPopularProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(cacheService.getById(id));
    }

    @GetMapping("/campaign")
    public ResponseEntity<List<ProductResponseDto>> getCampaignProducts(@AuthenticationPrincipal AuthDto auth) {
        return ResponseEntity.ok(productService.getCampaignProducts(auth));
    }

    @PostMapping(value = "/{id}/attachments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public AttachmentResponseDto updateImage(
            @PathVariable("id") UUID productId,
            @RequestPart MultipartFile file,
            @RequestParam int position) {
        return attachmentService.uploadProductImage(file, productId, position);
    }

}
