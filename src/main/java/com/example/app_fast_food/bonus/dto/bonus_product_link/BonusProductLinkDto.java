package com.example.app_fast_food.bonus.dto.bonus_product_link;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BonusProductLinkDto {
    @NotNull
    protected UUID bonusId;

    @NotNull
    protected UUID productId;

    protected int quantity = 1;
}
