package com.example.app_fast_food.bonus.dto.bonus;

import java.util.List;
import com.example.app_fast_food.bonus.dto.bonus_product_link.BonusProductLinkCreateDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BonusCreateDto extends BonusDto {

    private List<BonusProductLinkCreateDTO> bonusProductLinks;
}
