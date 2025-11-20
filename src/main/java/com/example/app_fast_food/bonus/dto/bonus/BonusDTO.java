package com.example.app_fast_food.bonus.dto.bonus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BonusDTO {
    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    private boolean isActive;

    private int usageLimit;
}
