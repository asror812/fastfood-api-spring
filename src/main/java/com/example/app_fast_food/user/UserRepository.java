package com.example.app_fast_food.user;

import com.example.app_fast_food.user.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumber(String phoneNumber);

    @Query(value = "SELECT * FROM users WHERE id = :userId", nativeQuery = true)
    User findUserById(UUID userId);

    @Query("""
            SELECT u FROM User u LEFT JOIN FETCH u.favouriteProducts WHERE u.id = :userId
            """)
    Optional<User> findUserByIdWithFavouriteProducts(@Param("userId") UUID userId);

}
