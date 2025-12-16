package com.example.app_fast_food.discount;

import com.example.app_fast_food.discount.dto.DiscountResponseDto;
import com.example.app_fast_food.discount.dto.DiscountUpdateDto;
import com.example.app_fast_food.discount.entity.Discount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DiscountMapper {

    public DiscountResponseDto toResponseDTO(Discount discount);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    public void toEntity(DiscountUpdateDto discountUpdateDTO, @MappingTarget Discount discount);

}
