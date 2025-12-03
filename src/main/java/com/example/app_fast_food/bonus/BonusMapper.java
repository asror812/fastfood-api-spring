package com.example.app_fast_food.bonus;

import com.example.app_fast_food.bonus.dto.bonus.BonusCreateDto;
import com.example.app_fast_food.bonus.dto.bonus.BonusResponseDto;
import com.example.app_fast_food.bonus.dto.bonus.BonusUpdateRequestDto;
import com.example.app_fast_food.bonus.entity.Bonus;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = { BonusProductLinkMapper.class, BonusConditionMapper.class })
public interface BonusMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "condition", ignore = true)
    @Mapping(target = "bonusProductLinks", ignore = true)
    Bonus toEntity(BonusCreateDto dto);

    BonusResponseDto toResponseDto(Bonus bonus);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bonusProductLinks", ignore = true)
    void toEntity(BonusUpdateRequestDto dto, @MappingTarget Bonus bonus);
}
