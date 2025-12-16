package com.example.app_fast_food.bonus.dto.bonus_condition;

import com.example.app_fast_food.bonus.entity.ConditionType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BonusConditionDto {
    @NotNull
    protected ConditionType conditionType;

    @NotBlank
    protected String value;
}
