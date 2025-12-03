package com.example.app_fast_food.bonus.dto.bonus_product_link;

import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BonusProductLinkResponseDTO extends BonusProductLinkDto {
    private UUID id;
    private String bonusName;

    public BonusProductLinkResponseDTO(UUID id, String bonusName, UUID bonusId, UUID productId, int quantity) {
        super(bonusId, productId, quantity);
        this.id = id;
        this.bonusName = bonusName;
    }
}
