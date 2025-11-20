package com.example.app_fast_food.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class ReviewUpdateRequestDTO {

    @Max(5)
    @Min(0)
    private Integer rating;
}
