package com.example.app_fast_food.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInDto {
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+\\d{9}$", message = "Phone number must be like +998901234567")
    private String phoneNumber;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
}
