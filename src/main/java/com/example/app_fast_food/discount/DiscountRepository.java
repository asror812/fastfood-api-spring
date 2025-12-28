package com.example.app_fast_food.discount;

import com.example.app_fast_food.discount.entity.Discount;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, UUID> {

    Optional<Discount> findDiscountByName(String name);

    @Query("FROM Discount d WHERE d.isActive = true AND d.requiredQuantity = 0 AND CURRENT_DATE BETWEEN d.startDate AND d.endDate")
    List<Discount> getActiveHolidayDiscounts();

    @Query("""
                SELECT d
                FROM Discount d
                JOIN d.products p
                WHERE d.requiredQuantity <= :quantity
                  AND p.id = :productId
                  AND d.startDate <= CURRENT_DATE
                  AND d.endDate >= CURRENT_DATE
                  AND d.isActive = true
                ORDER BY d.requiredQuantity DESC
            """)
    List<Discount> findProductQuantityDiscounts(@Param("productId") UUID productId, @Param("quantity") int quantity);

    @Query("SELECT d FROM Discount d LEFT JOIN FETCH d.products WHERE d.name = :name")
    Optional<Discount> findByNameWithProducts(@Param("name") String name);

    @Override
    @EntityGraph(attributePaths = {
            "products",
            "products.product",
            "products.product.category"
    })
    @NonNull
    List<Discount> findAll();

    @EntityGraph(attributePaths = {
            "products",
            "products.product",
            "products.product.category"
    })
    @NonNull
    Optional<Discount> findById(@NonNull UUID id);
}
