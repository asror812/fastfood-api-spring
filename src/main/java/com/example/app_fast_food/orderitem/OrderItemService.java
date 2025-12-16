package com.example.app_fast_food.orderitem;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import com.example.app_fast_food.orderitem.dto.OrderItemResponseDto;
import com.example.app_fast_food.orderitem.entity.OrderItem;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemMapper mapper;

    public List<OrderItemResponseDto> getResponseDTOS(List<OrderItem> orderItems) {
        return orderItems.stream().map(mapper::toResponseDTO)
                .toList();
    }
}
