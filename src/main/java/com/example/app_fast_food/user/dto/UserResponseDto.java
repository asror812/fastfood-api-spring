package com.example.app_fast_food.user.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.example.app_fast_food.product.dto.ProductDto;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDto extends UserDto {

    private String department;

    private Integer accessLevel;

    private Set<ProductDto> favouriteProducts = Set.of();

    private List<UserBonusDto> userBonuses = new ArrayList<>();

}
