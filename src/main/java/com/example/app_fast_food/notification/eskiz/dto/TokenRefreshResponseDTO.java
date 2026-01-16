package com.example.app_fast_food.notification.eskiz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenRefreshResponseDTO {
    private String message;

    @JsonProperty("token_type")
    private String tokenType;

    Map<String, String> data;
}
