package com.example.app_fast_food.category.dto;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParentCategoryDto implements Serializable{
    private UUID id;
    private String name;
}
