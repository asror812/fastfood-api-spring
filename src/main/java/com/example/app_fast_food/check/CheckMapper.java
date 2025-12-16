package com.example.app_fast_food.check;

import com.example.app_fast_food.check.dto.CheckCreateDto;
import com.example.app_fast_food.check.dto.CheckResponseDto;
import com.example.app_fast_food.check.entity.Check;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CheckMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "courier", ignore = true)
    @Mapping(target = "order.id", source = "orderId")
    @Mapping(target = "user.id", source = "userId")
    public Check toEntity(CheckCreateDto checkCreateDTO);

    @Mapping(target = "orderId", source = "order.id")
    @Mapping(target = "userId", source = "user.id")
    public CheckResponseDto toResponseDto(Check check);

}
