package com.example.app_fast_food.bonus.dto.bonus;

import com.example.app_fast_food.bonus.dto.bonus_condition.BonusConditionUpdateDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BonusUpdateRequestDTO extends BonusDTO {
    private BonusConditionUpdateDTO condition;
}
