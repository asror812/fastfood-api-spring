package com.example.app_fast_food.review;

import com.example.app_fast_food.common.mapper.BaseMapper;
import com.example.app_fast_food.review.dto.ReviewCreateRequestDTO;
import com.example.app_fast_food.review.dto.ReviewResponseDTO;
import com.example.app_fast_food.review.entity.Review;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper
        extends BaseMapper<Review, ReviewResponseDTO> {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "product.id", source = "productId")
    public Review toEntity(ReviewCreateRequestDTO ReviewCreateDTO);

    @Override
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "userId", ignore = true)
    public ReviewResponseDTO toResponseDTO(Review review);
}
