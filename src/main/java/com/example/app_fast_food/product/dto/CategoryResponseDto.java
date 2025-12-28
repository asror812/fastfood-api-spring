package com.example.app_fast_food.product.dto;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponseDto implements Serializable{

    private UUID id;
    private String name;
}