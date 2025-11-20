package com.example.app_fast_food.user;

import com.example.app_fast_food.common.repository.GenericRepository;
import com.example.app_fast_food.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends GenericRepository<User, UUID> {

     Optional<User> findByPhoneNumber(String phoneNumber);

     boolean existsByPhoneNumber(String phoneNumber);
}
