package com.example.app_fast_food.order.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

import com.example.app_fast_food.order.entity.PaymentType;
import com.example.app_fast_food.orderItem.dto.OrderItemCreateRequestDTO;

@Getter
@Setter
public class OrderCreateDto {

    private List<OrderItemCreateRequestDTO> orderItems;

    private PaymentType paymentType;
}
