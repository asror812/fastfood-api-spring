package com.example.app_fast_food.bonus.dto.bonus;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

import com.example.app_fast_food.bonus.dto.bonus_condition.BonusConditionResponseDto;

@Getter
@Setter
public class BonusDto {
    protected UUID id;

    protected String name;

    protected LocalDate startDate;

    protected LocalDate endDate;

    private BonusConditionResponseDto condition;

    protected boolean isActive;

    protected int usageLimit;
}
