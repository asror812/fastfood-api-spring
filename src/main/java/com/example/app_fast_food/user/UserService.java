package com.example.app_fast_food.user;

import com.example.app_fast_food.common.mapper.BaseMapper;
import com.example.app_fast_food.common.service.GenericService;
import com.example.app_fast_food.user.dto.UserResponseDTO;
import com.example.app_fast_food.user.entity.User;

import lombok.Getter;

import org.springframework.stereotype.Service;

@Service
@Getter
public class UserService extends GenericService<User, UserResponseDTO> {

    private final UserMapper mapper;

    private Class<User> entityClass = User.class;

    private final UserRepository repository;

    public UserService(BaseMapper<User, UserResponseDTO> baseMapper, UserMapper mapper,
            UserRepository repository) {
        super(baseMapper);

        this.mapper = mapper;
        this.repository = repository;
    }
}
