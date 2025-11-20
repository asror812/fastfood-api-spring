package com.example.app_fast_food.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDTO {

    private UUID id;

    private Integer rating;

    private String comment;

    private UUID userId;

    private UUID productId;
}
