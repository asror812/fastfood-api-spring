package com.example.app_fast_food.product;

import com.example.app_fast_food.product.entity.Product;

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

    @EntityGraph(attributePaths = {
            "category", "images",
            "images.attachment",
            "discounts.discount",
            "bonuses.bonus.condition"
    })
    @Query("""
                select distinct p
                from Product p
                join p.discounts pd
                join pd.discount d
                where d.active = true and :today between d.startDate and d.endDate
            """)
    List<Product> getCampaignProducts(@Param("today") LocalDate today);

    @Query(value = """
                SELECT p.*
                FROM products p
                JOIN order_items oi ON oi.product_id = p.id
                JOIN orders o ON o.id = oi.order_id
                WHERE o.status = 'COMPLETED'
                GROUP BY p.id
                ORDER BY SUM(oi.quantity) DESC
                LIMIT 4
            """, nativeQuery = true)
    List<Product> getPopularProducts();

    @EntityGraph(attributePaths = {
            "category",
            "images",
            "images.attachment"
    })
    @Query("select p from Product p")
    List<Product> findAllProductsDetails();

    @EntityGraph(attributePaths = {
            "category",
            "images",
            "images.attachment",
            "discounts.discount",
            "bonuses.bonus.condition"
    })
    Optional<Product> findProductDetailsById(@Param("id") UUID id);
}
