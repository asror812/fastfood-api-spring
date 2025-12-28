package com.example.app_fast_food.user;

import com.example.app_fast_food.user.entity.User;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @EntityGraph(attributePaths = { "customerProfile", "roles", "roles.permissions" })
    Optional<User> findByPhoneNumber(String phoneNumber);

    @EntityGraph(attributePaths = { "customerProfile", "roles", "roles.permissions" })
    @NonNull
    Optional<User> findById(@NonNull UUID id);

    boolean existsByPhoneNumber(String phoneNumber);
}
