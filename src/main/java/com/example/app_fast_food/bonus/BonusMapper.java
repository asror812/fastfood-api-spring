package com.example.app_fast_food.bonus;

import com.example.app_fast_food.bonus.dto.bonus.BonusCreateRequestDTO;
import com.example.app_fast_food.bonus.dto.bonus.BonusResponseDTO;
import com.example.app_fast_food.bonus.dto.bonus.BonusUpdateRequestDTO;
import com.example.app_fast_food.bonus.entity.Bonus;
import com.example.app_fast_food.common.mapper.BaseMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = { BonusProductLinkMapper.class, BonusConditionMapper.class })
public interface BonusMapper extends BaseMapper<Bonus, BonusResponseDTO> {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "condition", ignore = true)
    @Mapping(target = "bonusProductLinks", ignore = true)
    Bonus toEntity(BonusCreateRequestDTO dto);

    @Override
    BonusResponseDTO toResponseDTO(Bonus bonus);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bonusProductLinks", ignore = true)
    void toEntity(BonusUpdateRequestDTO dto, @MappingTarget Bonus bonus);
}
