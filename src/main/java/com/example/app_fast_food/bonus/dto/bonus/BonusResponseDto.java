package com.example.app_fast_food.bonus.dto.bonus;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.example.app_fast_food.bonus.dto.bonus_condition.BonusConditionResponseDto;
import com.example.app_fast_food.product.dto.ProductDto;

@Getter
@Setter
@NoArgsConstructor
public class BonusResponseDto implements Serializable {

    protected UUID id;

    protected String name;

    protected LocalDate startDate;

    protected LocalDate endDate;

    protected int usageLimit;

    private BonusConditionResponseDto condition;

    private List<ProductDto> products;
}
