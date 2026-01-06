package com.example.app_fast_food.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserDto {
    @NotBlank
    protected String name;

    @NotBlank
    @Pattern(regexp = "^\\+\\d{9}$")
    protected String phoneNumber;

    @NotNull
    protected LocalDate birthDate;

}
