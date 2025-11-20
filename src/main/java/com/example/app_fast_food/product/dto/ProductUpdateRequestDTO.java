package com.example.app_fast_food.product.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.example.app_fast_food.attachment.entity.Attachment;
import com.example.app_fast_food.bonus.entity.BonusProductLink;
import com.example.app_fast_food.category.dto.CategoryUpdateDto;
import com.example.app_fast_food.discount.entity.Discount;
import com.example.app_fast_food.review.entity.Review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductUpdateRequestDTO {
    private String name;

    private BigDecimal price;

    private CategoryUpdateDto category;

    private int weight;

    private Attachment main;

    private Attachment other;

    private List<Review> reviews = new ArrayList<>();

    private List<BonusProductLink> bonusProductLinks = new ArrayList<>();
}
