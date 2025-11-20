package com.example.app_fast_food.review;

import com.example.app_fast_food.common.repository.GenericRepository;
import com.example.app_fast_food.review.entity.Review;

import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReviewRepository extends GenericRepository<Review, UUID> {
}
