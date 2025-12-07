package com.example.app_fast_food.product;

import com.example.app_fast_food.bonus.BonusProductLinkMapper;
import com.example.app_fast_food.bonus.dto.bonus_product_link.BonusProductLinkResponseDTO;
import com.example.app_fast_food.bonus.entity.Bonus;
import com.example.app_fast_food.bonus.entity.BonusProductLink;
import com.example.app_fast_food.category.CategoryMapper;
import com.example.app_fast_food.discount.entity.Discount;
import com.example.app_fast_food.product.dto.ProductCreateDto;
import com.example.app_fast_food.product.dto.ProductResponseDto;
import com.example.app_fast_food.product.dto.ProductUpdateDto;
import com.example.app_fast_food.product.entity.Product;
import com.example.app_fast_food.product_discounts.dto.ProductDiscountResponseDto;
import com.example.app_fast_food.product_discounts.entity.ProductDiscount;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = { CategoryMapper.class, BonusProductLinkMapper.class })
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bonusProductLinks", ignore = true)
    @Mapping(target = "productDiscounts", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "mainImage", ignore = true)
    @Mapping(target = "secondaryImage", ignore = true)
    public Product toEntity(ProductCreateDto productCreateDto);

    public ProductResponseDto toResponseDTO(Product product);

    @Mapping(target = "id", ignore = true)
    public void toEntity(ProductUpdateDto productUpdateDTO, @MappingTarget Product product);

    default ProductDiscountResponseDto mapProductDiscount(ProductDiscount pd) {
        if (pd == null) {
            return null;
        }

        Discount d = pd.getDiscount();
        Product p = pd.getProduct();

        return new ProductDiscountResponseDto(
                pd.getId(),
                p.getId(),
                d.getId(),
                d.getName(),
                d.getPercentage(),
                d.getStartDate(),
                d.getEndDate(),
                d.getRequiredQuantity(),
                d.isActive());
    }

    default List<BonusProductLinkResponseDTO> mapProductDiscount(List<BonusProductLink> bps) {
        List<BonusProductLinkResponseDTO> bonusProductLinkResponseDTOs = new ArrayList<>();

        if (bps == null || bps.isEmpty()) {
            return Collections.emptyList();
        }

        for (BonusProductLink bp : bps) {

            Product p = bp.getProduct();
            Bonus b = bp.getBonus();

            bonusProductLinkResponseDTOs.add(new BonusProductLinkResponseDTO(
                    bp.getId(),
                    b.getName(),
                    b.getId(),
                    p.getId(),
                    bp.getQuantity()));
        }

        return bonusProductLinkResponseDTOs;
    }

}
