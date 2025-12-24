package com.example.app_fast_food.order.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.example.app_fast_food.order.entity.OrderStatus;
import com.example.app_fast_food.order.entity.PaymentType;
import com.example.app_fast_food.orderitem.dto.OrderItemResponseDto;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponseDto {

    private UUID id;

    private List<OrderItemResponseDto> orderItems;

    private OrderStatus status;

    private PaymentType paymentType;

    private BigDecimal totalPrice;

    private BigDecimal discountAmount;

    private BigDecimal finalPrice;
}
