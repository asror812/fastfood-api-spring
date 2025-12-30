package com.example.app_fast_food.order.dto;

import com.example.app_fast_food.order.entity.OrderStatus;
import com.example.app_fast_food.order.entity.PaymentType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderUpdateDto {
    private OrderStatus status;

    private PaymentType paymentType;
}
