package com.example.app_fast_food.product.dto;

import com.example.app_fast_food.attachment.dto.AttachmentResponseDTO;
import com.example.app_fast_food.bonus.dto.bonus_product_link.BonusProductLinkResponseDTO;
import com.example.app_fast_food.category.dto.CategoryResponseDTO;
import com.example.app_fast_food.product_discounts.ProductDiscountResponseDTo;
import com.example.app_fast_food.review.dto.ReviewResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductResponseDTO {
    private UUID id;

    private String name;

    private BigDecimal price;

    private CategoryResponseDTO category;

    private int weight;

    private AttachmentResponseDTO main;

    private AttachmentResponseDTO other;

    private List<ReviewResponseDTO> reviews;

    private List<ProductDiscountResponseDTo> discounts;

    private List<BonusProductLinkResponseDTO> bonusProductLinks;
}
