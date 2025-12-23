package com.example.app_fast_food.bonus.dto.bonus;

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
public class BonusResponseDto extends BonusDto {
    private UUID id;

    private BonusConditionResponseDto condition;

    private List<ProductDto> products;
}
