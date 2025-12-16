package com.example.app_fast_food.productdiscount;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.app_fast_food.productdiscount.dto.ProductDiscountResponseDto;
import com.example.app_fast_food.productdiscount.entity.ProductDiscount;

@Mapper(componentModel = "spring")
public interface ProductDiscountMapper {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "discountId", source = "discount.id")
    @Mapping(target = "name", source = "discount.name")
    @Mapping(target = "percentage", source = "discount.percentage")
    @Mapping(target = "startDate", source = "discount.startDate")
    @Mapping(target = "endDate", source = "discount.endDate")
    @Mapping(target = "requiredQuantity", source = "discount.requiredQuantity")
    @Mapping(target = "active", source = "discount.active")
    ProductDiscountResponseDto toResponseDTO(ProductDiscount product);
}
