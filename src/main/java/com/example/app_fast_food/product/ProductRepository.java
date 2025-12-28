package com.example.app_fast_food.product;

import com.example.app_fast_food.product.entity.Product;

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
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("""
            SELECT p
            FROM Product p
            JOIN FETCH p.category c
            WHERE c.name = :name
            """)
    List<Product> findProductsByCategoryName(@Param("name") String name);

    @Query(value = """
            WITH RECURSIVE subcats AS (
                SELECT id
                FROM categories
                WHERE id = :id

                UNION ALL

                SELECT c.id
                FROM categories c
                INNER JOIN subcats sc ON c.parent_id = sc.id
            )

            SELECT p.*
            FROM products p
            WHERE p.category_id = :id OR p.category_id IN (SELECT id FROM subcats)
            """, nativeQuery = true)
    List<Product> findProductsByCategoryTree(@Param("id") UUID id);

    Optional<Product> findByName(String name);

    @Query(value = """
            SELECT DISTINCT p.*
            FROM products p
            JOIN product_discounts pd ON pd.product_id = p.id
            JOIN discounts d ON d.id = pd.discount_id
            WHERE d.is_active = true AND CURRENT_DATE BETWEEN d.start_date AND d.end_date
            """, nativeQuery = true)
    List<Product> getCampaignProducts();

    @Query(value = """
            SELECT p.*
            FROM products p
            JOIN order_items oi ON oi.product_id = p.id
            GROUP BY p.id
            ORDER BY COUNT(oi.product_id) DESC
            LIMIT 4
            """, nativeQuery = true)
    List<Product> getPopularProducts();

    @Override
    @EntityGraph(attributePaths = {
            "category",
            "images",
            "discounts.discount",
            "bonuses.bonus.condition"
    })
    @NonNull
    List<Product> findAll();

    @Override
    @EntityGraph(attributePaths = {
            "category",
            "images",
            "discounts.discount",
            "bonuses.bonus.condition"
    })

    @NonNull
    Optional<Product> findById(@NonNull UUID id);
}
