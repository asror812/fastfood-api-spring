package com.example.app_fast_food.bonus.dto.bonus_condition;

import java.io.Serializable;
import java.util.UUID;

import com.example.app_fast_food.bonus.entity.ConditionType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BonusConditionResponseDto implements Serializable {

    private UUID id;

    private ConditionType conditionType;

    private String value;
}
