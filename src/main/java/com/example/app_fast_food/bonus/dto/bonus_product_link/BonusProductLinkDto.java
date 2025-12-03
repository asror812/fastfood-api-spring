package com.example.app_fast_food.bonus.dto.bonus_product_link;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BonusProductLinkDto {
    @NotNull
    private UUID bonusId;

    @NotNull
    private UUID productId;

    private int quantity = 1;
}
