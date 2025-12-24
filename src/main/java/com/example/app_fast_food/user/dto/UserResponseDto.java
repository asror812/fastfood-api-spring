package com.example.app_fast_food.user.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.example.app_fast_food.bonus.dto.bonus.BonusDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto extends UserDto {

    private Set<UUID> favouriteProducts = Set.of();

    private List<BonusDto> userBonuses = new ArrayList<>();

    private Set<String> roles = Set.of();
}
