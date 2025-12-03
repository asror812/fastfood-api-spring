package com.example.app_fast_food.bonus.dto.bonus_condition;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BonusConditionResponseDto extends BonusConditionDto {

    private UUID id;
}
