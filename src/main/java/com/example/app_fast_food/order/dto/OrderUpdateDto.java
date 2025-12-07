package com.example.app_fast_food.order.dto;

import com.example.app_fast_food.bonus.dto.bonus.BonusUpdateRequestDto;
import com.example.app_fast_food.order.entity.OrderStatus;
import com.example.app_fast_food.order.entity.PaymentType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderUpdateDto {
    private OrderStatus orderStatus;

    private PaymentType paymentType;

    private BonusUpdateRequestDto appliedBonus;
}
