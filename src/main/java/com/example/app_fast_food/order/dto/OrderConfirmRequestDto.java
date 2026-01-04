package com.example.app_fast_food.order.dto;

import com.example.app_fast_food.order.entity.PaymentType;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderConfirmRequestDto {
    @NotNull
    private PaymentType paymentType;
    @NotNull
    private AddressDto address;
}
