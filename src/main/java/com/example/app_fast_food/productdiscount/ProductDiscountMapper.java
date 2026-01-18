package com.example.app_fast_food.productdiscount;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.app_fast_food.product.dto.ProductDiscountDto;

@Mapper(componentModel = "spring")
public interface ProductDiscountMapper {

    @Mapping(target = "name", source = "discount.name")
    @Mapping(target = "percentage", source = "discount.percentage")
    @Mapping(target = "requiredQuantity", source = "discount.requiredQuantity")
    ProductDiscountDto toResponseDTO(ProductDiscount product);
}