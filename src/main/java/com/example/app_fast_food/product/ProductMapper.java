package com.example.app_fast_food.product;

import com.example.app_fast_food.bonus.BonusProductLinkMapper;
import com.example.app_fast_food.category.CategoryMapper;
import com.example.app_fast_food.common.mapper.BaseMapper;
import com.example.app_fast_food.product.dto.ProductCreateRequestDTO;
import com.example.app_fast_food.product.dto.ProductResponseDTO;
import com.example.app_fast_food.product.dto.ProductUpdateRequestDTO;
import com.example.app_fast_food.product.entity.Product;
import com.example.app_fast_food.review.ReviewMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = { CategoryMapper.class, BonusProductLinkMapper.class, ReviewMapper.class })
public interface ProductMapper
        extends BaseMapper<Product, ProductResponseDTO> {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bonusProductLinks", ignore = true)
    @Mapping(target = "discounts", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    public Product toEntity(ProductCreateRequestDTO productCreateDTO);

    @Override
    public ProductResponseDTO toResponseDTO(Product product);


    
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "id", ignore = true)
    public void toEntity(ProductUpdateRequestDTO productUpdateDTO, @MappingTarget Product product);
}
