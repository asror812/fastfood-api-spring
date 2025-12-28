package com.example.app_fast_food.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import com.example.app_fast_food.bonus.dto.bonus_condition.BonusConditionResponseDto;

@Getter
@Setter
public class UserBonusDto implements Serializable{
    private UUID id; 
    
    protected String name;

    protected LocalDate startDate;

    protected LocalDate endDate;

    protected BonusConditionResponseDto condition;

    protected boolean isActive;

    protected int usageLimit;
}
