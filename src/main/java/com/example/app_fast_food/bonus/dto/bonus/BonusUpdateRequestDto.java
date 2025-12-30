package com.example.app_fast_food.bonus.dto.bonus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BonusUpdateRequestDto extends BonusDto {
    private boolean active;
}
