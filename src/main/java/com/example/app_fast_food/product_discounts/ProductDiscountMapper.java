package com.example.app_fast_food.product_discounts;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.app_fast_food.common.mapper.BaseMapper;

@Mapper(componentModel = "spring")
public interface ProductDiscountMapper extends BaseMapper<ProductDiscount, ProductDiscountResponseDTo> {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "discountId", source = "discount.id")
    @Override
    public ProductDiscountResponseDTo toResponseDTO(ProductDiscount product);

}
