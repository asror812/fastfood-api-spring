package com.example.app_fast_food.bonus;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.app_fast_food.bonus.dto.bonus_product_link.BonusProductLinkResponseDTO;
import com.example.app_fast_food.bonus.entity.BonusProductLink;
import com.example.app_fast_food.common.mapper.BaseMapper;

@Mapper(componentModel = "spring")
public interface BonusProductLinkMapper extends BaseMapper<BonusProductLink, BonusProductLinkResponseDTO> {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "bonusId", source = "bonus.id")
    BonusProductLinkResponseDTO toResponseDTO(BonusProductLink bonusProductLink);
}
