package com.example.app_fast_food.check.dto;

import com.example.app_fast_food.filial.entity.Filial;
import com.example.app_fast_food.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CheckDTO {

    private User userId;

    private Filial filialId;

    @NotNull
    private Double totalPrice;

    @NotBlank
    private String courier;
}
