package com.example.app_fast_food.product.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.app_fast_food.attachment.dto.AttachmentResponseDto;
import com.example.app_fast_food.bonus.dto.bonus_product_link.BonusProductLinkResponseDTO;
import com.example.app_fast_food.productdiscount.dto.ProductDiscountResponseDto;

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

    private List<ProductDiscountResponseDto> productDiscounts = new ArrayList<>();

    private List<BonusProductLinkResponseDTO> bonuses = new ArrayList<>();

    @Override
    public String toString() {
        return "\nProduct [" +
                "\n    name=" + name +
                ",\n    price=" + price +
                ",\n    category=" + (category != null ? category.getName() : "null") +
                ",\n    mainImage=" + (mainImage != null ? mainImage.getDownloadUrl() : "null") +
                ",\n    secondaryImage=" + (secondaryImage != null ? secondaryImage.getDownloadUrl() : "null") + "]";
    }
}
