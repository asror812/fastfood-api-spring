package com.example.app_fast_food.common.notification.eskiz.dto;


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

    @JsonProperty("monile_phone")
    public String phoneNumber;
    private String message;
    private final String from  = "4546";
}
