package com.example.app_fast_food.bonus.dto.bonus_condition;

import com.example.app_fast_food.bonus.entity.ConditionType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BonusConditionDTO {
    @NotNull
    private ConditionType conditionType;

    @NotBlank
    private String value;
}
