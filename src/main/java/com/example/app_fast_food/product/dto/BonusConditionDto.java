package com.example.app_fast_food.product.dto;

import java.io.Serializable;
import java.util.UUID;

import com.example.app_fast_food.bonus.entity.ConditionType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BonusConditionDto implements Serializable{
    private UUID id;

    private ConditionType conditionType;

    private String value;
}
