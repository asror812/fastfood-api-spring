package com.example.app_fast_food.order.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChosenOrderDto {
    @NotNull(message = "Bonus id is required")
    private UUID bonusId;

    @NotNull(message = "Bonus Product Link is required")
    private UUID bonusProductLinkId;
}
