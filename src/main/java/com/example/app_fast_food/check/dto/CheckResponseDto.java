package com.example.app_fast_food.check.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CheckResponseDto {
    private UUID id;

    private UUID orderId;

    private UUID userId;
    private String userName;

    private UUID filialId;
    private String filialName;

    private String courier;
}
