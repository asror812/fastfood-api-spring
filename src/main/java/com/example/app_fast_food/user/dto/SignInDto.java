package com.example.app_fast_food.user.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInDto {
    @NotBlank
    @Pattern(regexp = "^\\+\\d{9}$")
    private String phoneNumber;

    @NotBlank
    @Min(8)
    private String password;
}
