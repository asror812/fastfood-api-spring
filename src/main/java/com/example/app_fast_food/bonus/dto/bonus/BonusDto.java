package com.example.app_fast_food.bonus.dto.bonus;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import com.example.app_fast_food.bonus.dto.bonus_condition.BonusConditionResponseDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class BonusDto {

    @NotBlank
    protected String name;

    @NotNull
    protected LocalDate startDate;

    @NotNull
    protected LocalDate endDate;

    @NotNull
    protected BonusConditionResponseDto condition;

    @NotNull
    protected int usageLimit;
}
