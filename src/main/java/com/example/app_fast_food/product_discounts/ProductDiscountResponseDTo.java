package com.example.app_fast_food.product_discounts;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDiscountResponseDTo {
    private UUID id;

    private UUID productId;

    private UUID discountId;
}
