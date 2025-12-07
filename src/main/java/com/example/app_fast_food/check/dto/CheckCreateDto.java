package com.example.app_fast_food.check.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CheckCreateDto {

    @NotNull
    private UUID orderId;

    @NotNull
    private UUID userId;

    private String courier;

    private UUID filialId;
}
