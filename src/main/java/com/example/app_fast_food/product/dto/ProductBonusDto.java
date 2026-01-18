package com.example.app_fast_food.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductBonusDto implements Serializable {
    private UUID id;

    protected String name;

    protected LocalDate startDate;

    protected LocalDate endDate;

    protected BonusConditionDto condition;

    protected int usageLimit;
}
