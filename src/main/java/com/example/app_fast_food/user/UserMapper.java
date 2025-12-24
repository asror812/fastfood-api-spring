package com.example.app_fast_food.user;

import com.example.app_fast_food.bonus.dto.bonus.BonusDto;
import com.example.app_fast_food.bonus.entity.Bonus;
import com.example.app_fast_food.product.entity.Product;
import com.example.app_fast_food.user.dto.UserListResponseDto;
import com.example.app_fast_food.user.dto.UserResponseDto;
import com.example.app_fast_food.user.dto.UserUpdateDto;
import com.example.app_fast_food.user.entity.CustomerProfile;
import com.example.app_fast_food.user.entity.User;
import com.example.app_fast_food.user.role.Role;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "customerProfile", ignore = true)
    @Mapping(target = "adminProfile", ignore = true)
    public void toEntity(UserUpdateDto updateDto, @MappingTarget User user);

    @Mapping(target = "userBonuses", ignore = true)
    @Mapping(target = "favouriteProducts", ignore = true)
    public UserResponseDto toResponseDto(User user);

    public UserListResponseDto toListResponseDto(User user);

    default Set<String> mapRoles(Set<Role> roles) {
        if (roles == null || roles.isEmpty())
            return Collections.emptySet();

        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }

    @AfterMapping
    default void mapCustomerProfileData(User user, @MappingTarget UserResponseDto dto) {
        if (user.getCustomerProfile() == null) {
            return;
        }

        CustomerProfile profile = user.getCustomerProfile();

        if (profile.getFavouriteProducts() != null) {
            dto.setFavouriteProducts(profile.getFavouriteProducts().stream()
                    .map(Product::getId)
                    .collect(Collectors.toSet()));
        }

        if (profile.getUserBonuses() != null) {
            dto.setUserBonuses(profile.getUserBonuses().stream()
                    .map(userBonus -> mapToBonusDto(userBonus.getBonus()))
                    .toList());
        }
    }

    default BonusDto mapToBonusDto(Bonus bonus) {
        if (bonus == null)
            return null;

        BonusDto dto = new BonusDto();
        dto.setId(bonus.getId());
        dto.setName(bonus.getName());
        dto.setStartDate(bonus.getStartDate());
        dto.setEndDate(bonus.getEndDate());
        dto.setActive(bonus.isActive());
        dto.setUsageLimit(bonus.getUsageLimit());
        return dto;
    }

}
