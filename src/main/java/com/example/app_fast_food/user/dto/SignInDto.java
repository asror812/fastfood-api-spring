package com.example.app_fast_food.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInDto {
    @NotBlank
    @Pattern(regexp = "^\\+[0-9]{2} [0-9]{3}-[0-9]{2}-[0-9]{2}$")
    private String phoneNumber;

    @NotBlank
    private String password;
}
