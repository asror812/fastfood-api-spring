package com.example.app_fast_food.user.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserListResponseDto {
    private String name;

    private String phoneNumber;

    private LocalDate birthDate;
}
