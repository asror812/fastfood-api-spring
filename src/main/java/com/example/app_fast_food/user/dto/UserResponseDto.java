package com.example.app_fast_food.user.dto;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.example.app_fast_food.bonus.dto.user_bonus.UserBonusResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto extends UserDto {

    public Set<UUID> favouriteProducts;

    private List<UserBonusResponseDto> userBonuses;

    private Set<String> roles;
}
