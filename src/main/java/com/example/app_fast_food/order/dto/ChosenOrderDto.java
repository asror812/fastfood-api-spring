package com.example.app_fast_food.order.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChosenOrderDto {
    @NotNull
    private UUID bonusId;
    @NotNull
    private UUID bonunProductLinkId;
}
