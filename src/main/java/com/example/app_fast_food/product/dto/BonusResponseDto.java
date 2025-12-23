package com.example.app_fast_food.product.dto;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BonusResponseDto {
    private UUID id;

    private String name;

    private BonusConditionResponseDto condition;

    private int usageLimit;

    private LocalDate startDate;

    private LocalDate endDate;
}
