package com.example.app_fast_food.order.dto;

import com.example.app_fast_food.order.entity.PaymentType;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderConfirmRequestDto {
    @NotNull(message = "Payment type is required")
    private PaymentType paymentType;

    @NotNull(message = "Address is required")
    private AddressDto address;
}
