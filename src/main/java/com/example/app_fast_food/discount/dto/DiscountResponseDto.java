package com.example.app_fast_food.discount.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.example.app_fast_food.product.dto.ProductDto;

@Setter
@Getter
public class DiscountResponseDto implements Serializable {
    private UUID id;

    private String name;

    private int percentage;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer requiredQuantity;

    private List<ProductDto> products;

    private boolean active;
}
