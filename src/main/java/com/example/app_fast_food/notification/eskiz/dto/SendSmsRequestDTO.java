package com.example.app_fast_food.notification.eskiz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SendSmsRequestDTO {
    private static final String FROM = "4546";

    @JsonProperty("mobile_phone")
    public String phoneNumber;

    private String message;
}
