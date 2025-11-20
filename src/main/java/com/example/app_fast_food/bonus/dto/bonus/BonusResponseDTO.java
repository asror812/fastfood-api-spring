package com.example.app_fast_food.bonus.dto.bonus;

import java.util.List;
import java.util.UUID;

import com.example.app_fast_food.bonus.dto.bonus_condition.BonusConditionResponseDTO;
import com.example.app_fast_food.bonus.dto.bonus_product_link.BonusProductLinkResponseDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BonusResponseDTO extends BonusDTO {
    private UUID id;

    private BonusConditionResponseDTO condition;
    private List<BonusProductLinkResponseDTO> bonusProductLinks;
}
