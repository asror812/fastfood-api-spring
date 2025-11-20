package com.example.app_fast_food.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

import com.example.app_fast_food.order.entity.PaymentType;
import com.example.app_fast_food.orderItem.dto.OrderItemCreateRequestDTO;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderCreateRequestDTO {

    private List<OrderItemCreateRequestDTO> orderItems;

    private PaymentType paymentType;
}
