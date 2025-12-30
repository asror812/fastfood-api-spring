package com.example.app_fast_food.user;

import com.example.app_fast_food.user.entity.User;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @EntityGraph(attributePaths = {
            "roles",
            "roles.permissions"
    })
    @Query("select u from User u where u.phoneNumber = :phoneNumber")
    Optional<User> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @EntityGraph(attributePaths = { "customerProfile", "adminProfile" })
    @Query("select u from User u where u.id = :id")
    Optional<User> findUserDetailsById(@NonNull UUID id);

    boolean existsByPhoneNumber(String phoneNumber);
}
