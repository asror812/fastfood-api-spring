package com.example.app_fast_food.user;

import com.example.app_fast_food.exception.EntityNotFoundException;
import com.example.app_fast_food.user.dto.AuthDto;
import com.example.app_fast_food.user.dto.UserListResponseDto;
import com.example.app_fast_food.user.dto.UserResponseDto;
import com.example.app_fast_food.user.entity.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
@Getter
@RequiredArgsConstructor
public class UserService {

    private final UserMapper mapper;
    private final UserRepository repository;

    public UserResponseDto getMe(AuthDto auth) {
        User userWithBonuses = repository.findById(auth.getId())
                .orElseThrow(
                        () -> new EntityNotFoundException("User", auth.getId().toString()));

        return mapper.toResponseDto(userWithBonuses);
    }

    public List<UserListResponseDto> getAll() {
        return repository.findAll().stream().map(mapper::toListResponseDto).toList();
    }
}
