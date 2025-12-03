package com.example.app_fast_food.orderItem;

import com.example.app_fast_food.orderItem.dto.OrderItemResponseDto;
import com.example.app_fast_food.orderItem.entity.OrderItem;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
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
