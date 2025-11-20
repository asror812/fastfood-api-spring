package com.example.app_fast_food.order.dto;

import com.example.app_fast_food.bonus.dto.bonus.BonusUpdateRequestDTO;
import com.example.app_fast_food.order.entity.OrderStatus;
import com.example.app_fast_food.order.entity.PaymentType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateRequestDTO {
    private OrderStatus orderStatus;

    private PaymentType paymentType;

    private BonusUpdateRequestDTO appliedBonus;
}
