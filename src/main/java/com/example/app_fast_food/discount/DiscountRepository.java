package com.example.app_fast_food.discount;

import com.example.app_fast_food.discount.entity.Discount;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, UUID> {

    @Query("select d from Discount d left join fetch d.products where d.name = :name and d.active = true")
    Optional<Discount> findByNameWithProducts(@Param("name") String name);

    @EntityGraph(attributePaths = {
            "products",
            "products.product",
            "products.product.category"
    })
    @Query("select d from Discount d where d.active = true and :today between d.startDate and d.endDate")
    List<Discount> findAllActiveDiscountsDetails(@Param("today") LocalDate today);

    @EntityGraph(attributePaths = {
            "products",
            "products.product",
            "products.product.category"
    })
    @Query("select d from Discount d where d.id = :id and d.active = true and :today between d.startDate and d.endDate")
    Optional<Discount> findDiscountDetails(@Param("id") UUID id, @Param("today") LocalDate today);
}
