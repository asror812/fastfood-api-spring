package com.example.app_fast_food.bonus;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.app_fast_food.bonus.dto.bonus_condition.BonusConditionResponseDTO;
import com.example.app_fast_food.bonus.dto.bonus_condition.BonusConditionUpdateDTO;
import com.example.app_fast_food.bonus.entity.BonusCondition;
import com.example.app_fast_food.common.mapper.BaseMapper;

@Mapper(componentModel = "spring")
public interface BonusConditionMapper extends BaseMapper<BonusCondition, BonusConditionResponseDTO> {

    @Mapping(target = "id", ignore = true)
    void toEntity(BonusConditionUpdateDTO conditionUpdateDTO, @MappingTarget BonusCondition condition);
}
