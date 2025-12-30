package com.example.app_fast_food.product.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import com.example.app_fast_food.bonus.dto.bonus_condition.BonusConditionResponseDto;

@Getter
@Setter
public class ProductBonusDto implements Serializable {
    private UUID id;

    protected String name;

    protected LocalDate startDate;

    protected LocalDate endDate;

    protected BonusConditionResponseDto condition;

    protected int usageLimit;
}
