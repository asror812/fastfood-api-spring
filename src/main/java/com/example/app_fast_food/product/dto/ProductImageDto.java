package com.example.app_fast_food.product.dto;

import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImageDto {
    @NotNull
    private UUID attachmentId;

    @Min(value = 1)
    private int position;
}
