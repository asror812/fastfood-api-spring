package com.example.app_fast_food.productdiscount;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.app_fast_food.product.dto.ProductDiscountResponseDto;

@Mapper(componentModel = "spring")
public interface ProductDiscountMapper {

    @Mapping(target = "name", source = "discount.name")
    @Mapping(target = "percentage", source = "discount.percentage")
    @Mapping(target = "startDate", source = "discount.startDate")
    @Mapping(target = "endDate", source = "discount.endDate")
    @Mapping(target = "requiredQuantity", source = "discount.requiredQuantity")
    ProductDiscountResponseDto toResponseDTO(ProductDiscount product);
}