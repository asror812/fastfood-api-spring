package com.example.app_fast_food.product_discounts;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.app_fast_food.product_discounts.dto.ProductDiscountResponseDto;
import com.example.app_fast_food.product_discounts.entity.ProductDiscount;

@Mapper(componentModel = "spring")
public interface ProductDiscountMapper {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "discountId", source = "discount.id")
    public ProductDiscountResponseDto toResponseDTO(ProductDiscount product);
}
