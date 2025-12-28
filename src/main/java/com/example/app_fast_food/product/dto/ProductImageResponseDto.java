package com.example.app_fast_food.product.dto;

import java.io.Serializable;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageResponseDto implements Serializable {
    private UUID id;
    private String downloadUrl;
    private int position;
}
