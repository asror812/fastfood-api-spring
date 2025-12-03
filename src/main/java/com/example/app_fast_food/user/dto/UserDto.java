package com.example.app_fast_food.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    @NotBlank
    private String name;

    @NotBlank
    @Pattern(regexp = "^\\+\\d{2} \\d{3}-\\d{2}-\\d{2}$")
    private String phoneNumber;

    @NotNull
    private LocalDate birthDate;
}
