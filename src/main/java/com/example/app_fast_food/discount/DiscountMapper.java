package com.example.app_fast_food.discount;

import com.example.app_fast_food.common.mapper.BaseMapper;
import com.example.app_fast_food.discount.dto.DiscountResponseDTO;
import com.example.app_fast_food.discount.dto.DiscountUpdateRequetDTO;
import com.example.app_fast_food.discount.entity.Discount;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DiscountMapper extends BaseMapper<Discount, DiscountResponseDTO> {

    @Override
    public DiscountResponseDTO toResponseDTO(Discount discount);

    public void toEntity(DiscountUpdateRequetDTO discountUpdateDTO, @MappingTarget Discount discount);

}
