package com.example.app_fast_food.product.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponseDto {

    private UUID id;
    private String name;
}