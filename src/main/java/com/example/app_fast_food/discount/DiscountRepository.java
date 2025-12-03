package com.example.app_fast_food.discount;

import com.example.app_fast_food.discount.entity.Discount;
import com.example.app_fast_food.product.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
            AND p = :product
            AND d.startDate <= CURRENT_DATE
            AND d.endDate >= CURRENT_DATE
            AND d.isActive = true
      """)
  List<Discount> findProductQuantityDiscounts(Product product, int quantity);

  @Query("select d from Discount d left join fetch d.products where d.name = :name")
  Optional<Discount> findByNameWithProducts(String name);
}
