package com.example.app_fast_food.user.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserListResponseDto {
    protected String name;

    protected String phoneNumber;

    protected LocalDate birthDate;
}
