package com.example.app_fast_food.bonus.dto.bonus;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BonusDto {
    protected String name;

    protected LocalDate startDate;

    protected LocalDate endDate;

    protected boolean isActive;

    protected int usageLimit;
}
