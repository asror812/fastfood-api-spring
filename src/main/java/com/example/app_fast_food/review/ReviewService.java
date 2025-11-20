package com.example.app_fast_food.review;

import com.example.app_fast_food.common.mapper.BaseMapper;
import com.example.app_fast_food.common.service.GenericService;
import com.example.app_fast_food.review.dto.ReviewResponseDTO;
import com.example.app_fast_food.review.entity.Review;

import lombok.Getter;

import org.springframework.stereotype.Service;

@Service
@Getter
public class ReviewService
        extends GenericService<Review, ReviewResponseDTO> {
    private final Class<Review> entityClass = Review.class;

    private final ReviewMapper mapper;
    private final ReviewRepository repository;

    public ReviewService(BaseMapper<Review, ReviewResponseDTO> baseMapper, ReviewMapper mapper,
            ReviewRepository repository) {
        super(baseMapper);

        this.mapper = mapper;
        this.repository = repository;
    }
}
