package com.example.app_fast_food.bonus.dto.bonus_product_link;

import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BonusProductLinkResponseDTO extends BonusProductLinkDTO {
    private UUID id;
}
