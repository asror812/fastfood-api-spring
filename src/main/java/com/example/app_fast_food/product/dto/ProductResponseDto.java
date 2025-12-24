package com.example.app_fast_food.product.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.app_fast_food.bonus.dto.bonus.BonusDto;

@Getter
@Setter
public class ProductResponseDto {
    private UUID id;
    private String name;
    private BigDecimal price;
    private CategoryResponseDto category;

    private int weight;

    private List<ProductDiscountResponseDto> discounts = new ArrayList<>();
    private List<BonusDto> bonuses = new ArrayList<>();
    private List<ProductImageResponseDto> images = new ArrayList<>();
}
