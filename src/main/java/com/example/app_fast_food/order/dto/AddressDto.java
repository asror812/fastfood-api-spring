package com.example.app_fast_food.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {
    @NotNull
    private double latitude;

    @NotNull
    private double longitude;
}
