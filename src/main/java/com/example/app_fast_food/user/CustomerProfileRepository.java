package com.example.app_fast_food.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.app_fast_food.user.entity.CustomerProfile;

@Repository
public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, UUID> {

    @Query("SELECT cp FROM CustomerProfile cp LEFT JOIN FETCH cp.favouriteProducts WHERE cp.id = :customerId")
    Optional<CustomerProfile> findWithFavouriteProductsByUserId(@Param("customerId") UUID customerId);
}
