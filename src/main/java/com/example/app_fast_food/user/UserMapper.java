package com.example.app_fast_food.user;

import com.example.app_fast_food.common.mapper.BaseMapper;
import com.example.app_fast_food.user.dto.SignUpDTO;
import com.example.app_fast_food.user.dto.UserResponseDTO;
import com.example.app_fast_food.user.dto.UserUpdateRequestDTO;
import com.example.app_fast_food.user.entity.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<User, UserResponseDTO> {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "checks", ignore = true)
    @Mapping(target = "userBonuses", ignore = true)
    @Mapping(target = "favoriteProducts", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "address", ignore = true)
    public User toEntity(SignUpDTO signUpDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "checks", ignore = true)
    @Mapping(target = "userBonuses", ignore = true)
    @Mapping(target = "favoriteProducts", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "address", ignore = true)
    public void toEntity(UserUpdateRequestDTO updateDto, @MappingTarget User user);

}
