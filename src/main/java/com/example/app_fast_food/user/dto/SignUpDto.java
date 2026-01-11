package com.example.app_fast_food.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpDto extends UserDto {

    @NotBlank
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
}
