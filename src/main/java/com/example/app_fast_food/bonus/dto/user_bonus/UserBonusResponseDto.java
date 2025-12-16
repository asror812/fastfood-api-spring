package com.example.app_fast_food.bonus.dto.user_bonus;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBonusResponseDto {
    private UUID id;

    private UUID bonusId;

    private String name;

    private int percentage;

    private int requiredQuantity;

    private boolean active;

    private LocalDate startDate;
    private LocalDate endDate;

    private LocalDate receivedDate;

    private boolean expired;
}
