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
    @NotBlank(message = "Name is required")
    protected String name;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+\\d{9}$")
    protected String phoneNumber;

    @NotNull(message = "Birth date is required")
    protected LocalDate birthDate;

}
