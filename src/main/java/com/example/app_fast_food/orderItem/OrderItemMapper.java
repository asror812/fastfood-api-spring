package com.example.app_fast_food.orderItem;

import com.example.app_fast_food.orderItem.dto.OrderItemCreateRequestDTO;
import com.example.app_fast_food.orderItem.dto.OrderItemResponseDto;
import com.example.app_fast_food.orderItem.dto.OrderItemUpdateRequestDTO;
import com.example.app_fast_food.orderItem.entity.OrderItem;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product.id", source = "productId")
    @Mapping(target = "quantity", ignore = true)
    OrderItem toEntity(OrderItemCreateRequestDTO orderItemCreateDTO);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "orderId", source = "order.id")
    OrderItemResponseDto toResponseDTO(OrderItem orderItem);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product", ignore = true)
    void toEntity(OrderItemUpdateRequestDTO orderItemUpdateDTO, @MappingTarget OrderItem orderItem);
}
