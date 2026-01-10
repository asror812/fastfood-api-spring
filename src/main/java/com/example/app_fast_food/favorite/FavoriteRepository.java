package com.example.app_fast_food.favorite;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, UUID> {

    boolean existsByCustomerIdAndProductId(UUID userId, UUID productId);

    void deleteByCustomerIdAndProductId(@Param("userId") UUID userId, @Param("productId") UUID productId);

    @Query("select f from Favorite f join fetch f.product p where f.customer.id = :userId")
    List<Favorite> findAllByUserId(@Param("userId") UUID userId);

    @Query("select f.product.id from Favorite f where f.customer.id = :userId")
    Set<UUID> findAllProductIdsByUserId(@Param("userId") UUID userId);

}
