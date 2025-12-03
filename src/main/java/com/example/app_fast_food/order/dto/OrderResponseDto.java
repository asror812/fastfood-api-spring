package com.example.app_fast_food.order.dto;

import java.util.List;

import com.example.app_fast_food.order.entity.OrderStatus;
import com.example.app_fast_food.order.entity.PaymentType;
import com.example.app_fast_food.orderItem.dto.OrderItemResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderResponseDto {
    private List<OrderItemResponseDto> orderItems;

    private OrderStatus orderStatus;

    private PaymentType paymentType;
}
