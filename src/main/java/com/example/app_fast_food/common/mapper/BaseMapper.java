package com.example.app_fast_food.common.mapper;

public interface BaseMapper<E, R> {
    R toResponseDTO(E entity);
}
