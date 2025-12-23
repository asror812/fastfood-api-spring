package com.example.app_fast_food.productimage;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImageResponseDto {
    private UUID id;

    private int position;

    private UUID productId;

    private UUID attachmentId;
}
