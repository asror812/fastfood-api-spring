package com.example.app_fast_food.bonus.dto.bonus;

import com.example.app_fast_food.bonus.dto.bonus_condition.BonusConditionUpdateDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BonusUpdateRequestDto extends BonusDto {
    private BonusConditionUpdateDto condition;
}
