package com.example.app_fast_food.order.dto;

import java.util.List;

import com.example.app_fast_food.order.entity.OrderStatus;
import com.example.app_fast_food.order.entity.PaymentType;
import com.example.app_fast_food.orderitem.dto.OrderItemResponseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponseDto {
    private List<OrderItemResponseDto> orderItems;

    private OrderStatus orderStatus;

    private PaymentType paymentType;
}
