package com.example.app_fast_food.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponseDto {
    private String token;

    public TokenResponseDto(String token) {
        this.token = token;
    }
}
