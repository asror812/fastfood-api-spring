package com.example.app_fast_food.bonus;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.app_fast_food.bonus.dto.bonus_condition.BonusConditionUpdateDto;
import com.example.app_fast_food.bonus.entity.BonusCondition;

@Mapper(componentModel = "spring")
public interface BonusConditionMapper {

    @Mapping(target = "id", ignore = true)
    void toEntity(BonusConditionUpdateDto conditionUpdateDTO, @MappingTarget BonusCondition condition);
}
