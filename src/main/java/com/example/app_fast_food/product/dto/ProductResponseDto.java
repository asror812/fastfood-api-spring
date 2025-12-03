package com.example.app_fast_food.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.example.app_fast_food.attachment.dto.AttachmentResponseDto;
import com.example.app_fast_food.bonus.dto.bonus_product_link.BonusProductLinkResponseDTO;
import com.example.app_fast_food.product_discounts.dto.ProductDiscountResponseDto;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductResponseDto {
    private UUID id;

    private String name;

    private BigDecimal price;

    private int weight;

    private CategoryResponseDto category;

    private AttachmentResponseDto mainImage;

    private AttachmentResponseDto secondaryImage;

    private List<ProductDiscountResponseDto> productDiscounts;

    private List<BonusProductLinkResponseDTO> bonusProductLinks;
}
