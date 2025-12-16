package com.example.app_fast_food.product;

import com.example.app_fast_food.bonus.BonusProductLinkMapper;
import com.example.app_fast_food.category.CategoryMapper;
import com.example.app_fast_food.product.dto.ProductCreateDto;
import com.example.app_fast_food.product.dto.ProductResponseDto;
import com.example.app_fast_food.product.dto.ProductUpdateDto;
import com.example.app_fast_food.product.entity.Product;
import com.example.app_fast_food.productdiscount.ProductDiscountMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = { CategoryMapper.class, BonusProductLinkMapper.class,
        ProductDiscountMapper.class })
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bonusProductLinks", ignore = true)
    @Mapping(target = "productDiscounts", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "mainImage", ignore = true)
    @Mapping(target = "secondaryImage", ignore = true)
    public Product toEntity(ProductCreateDto productCreateDto);

    @Mapping(target = "bonuses", source = "bonusProductLinks")
    public ProductResponseDto toResponseDTO(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "productDiscounts", ignore = true)
    @Mapping(target = "mainImage", ignore = true)
    @Mapping(target = "secondaryImage", ignore = true)
    @Mapping(target = "bonusProductLinks", ignore = true)
    public void toEntity(ProductUpdateDto productUpdateDTO, @MappingTarget Product product);

}
