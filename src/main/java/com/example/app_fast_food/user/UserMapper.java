package com.example.app_fast_food.user;

import com.example.app_fast_food.product.entity.Product;
import com.example.app_fast_food.user.dto.SignUpDto;
import com.example.app_fast_food.user.dto.UserListResponseDto;
import com.example.app_fast_food.user.dto.UserResponseDto;
import com.example.app_fast_food.user.dto.UserUpdateDto;
import com.example.app_fast_food.user.entity.User;
import com.example.app_fast_food.user.role.Role;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "checks", ignore = true)
    @Mapping(target = "userBonuses", ignore = true)
    @Mapping(target = "favouriteProducts", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "address", ignore = true)
    public User toEntity(SignUpDto signUpDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "checks", ignore = true)
    @Mapping(target = "userBonuses", ignore = true)
    @Mapping(target = "favouriteProducts", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "address", ignore = true)
    public void toEntity(UserUpdateDto updateDto, @MappingTarget User user);

    public UserResponseDto toResponseDto(User user);

    public UserListResponseDto toListResponseDto(User user);

    default Set<String> mapRoles(Set<Role> roles) {
        if (roles == null || roles.isEmpty())
            return Collections.emptySet();
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }

    default Set<UUID> mapFavoriteProducts(Set<Product> favoriteProducts) {
        if (favoriteProducts == null || favoriteProducts.isEmpty())
            return Collections.emptySet();

        return favoriteProducts.stream().map(Product::getId).collect(Collectors.toSet());
    }
}
