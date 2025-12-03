package com.example.app_fast_food.bonus;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.app_fast_food.bonus.dto.bonus_product_link.BonusProductLinkResponseDTO;
import com.example.app_fast_food.bonus.entity.BonusProductLink;

@Mapper(componentModel = "spring")
public interface BonusProductLinkMapper {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "bonusId", source = "bonus.id")
    BonusProductLinkResponseDTO toResponseDto(BonusProductLink bonusProductLink);
}
