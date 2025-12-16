package com.example.app_fast_food.check.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckDto {
    @NotNull
    protected UUID orderId;

    @NotNull
    protected UUID userId;

    @NotBlank
    protected String courier;
}
