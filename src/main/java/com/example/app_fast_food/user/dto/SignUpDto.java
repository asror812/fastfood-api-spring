package com.example.app_fast_food.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpDto extends UserDto {

    @NotBlank
    private String password;
}
