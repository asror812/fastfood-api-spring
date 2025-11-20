package com.example.app_fast_food.review.dto;

import java.util.UUID;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewCreateRequestDTO {

    @Max(5)
    @Min(0)
    private Integer rating;

    private UUID userId;

    private UUID productId;

    private String comment;
}
