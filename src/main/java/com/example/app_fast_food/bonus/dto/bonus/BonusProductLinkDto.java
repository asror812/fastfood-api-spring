package com.example.app_fast_food.bonus.dto.bonus;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BonusProductLinkDto {
    private UUID id;

    private ProductDto product;

    private int quantity = 1;
}
