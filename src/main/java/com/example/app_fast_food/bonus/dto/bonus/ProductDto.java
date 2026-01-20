package com.example.app_fast_food.bonus.dto.bonus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.app_fast_food.product.dto.ProductImageResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private UUID id;
    private String name;

    private List<ProductImageResponseDto> images = new ArrayList<>();
}
