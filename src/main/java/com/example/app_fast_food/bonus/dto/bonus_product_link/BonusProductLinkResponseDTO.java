package com.example.app_fast_food.bonus.dto.bonus_product_link;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BonusProductLinkResponseDTO extends BonusProductLinkDto {
    private UUID id;
    private String bonusName;
}
