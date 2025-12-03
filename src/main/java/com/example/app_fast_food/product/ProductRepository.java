package com.example.app_fast_food.product;

import com.example.app_fast_food.product.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query(value = """
            SELECT p.*
            FROM products p
            INNER JOIN categories c ON c.id = p.category_id
            WHERE c.name = :name
            """, nativeQuery = true)
    List<Product> findProductsByCategoryName(String name);

    @Query(value = """
            SELECT p.*
            FROM products p
            WHERE p.category_id = :id
            OR p.category_id IN (
                SELECT c.id FROM categories c WHERE c.parent_id = :id
            )
            """, nativeQuery = true)
    List<Product> findProductsByCategoryId(UUID id);

    Optional<Product> findProductById(UUID id);

    Optional<Product> findByName(String name);

    @Query(value = """
            SELECT DISTINCT p.*
            FROM products p
            JOIN product_discounts pd ON pd.product_id = p.id
            JOIN discounts d ON d.id = pd.discount_id
            WHERE d.is_active = true
              AND CURRENT_DATE BETWEEN d.start_date AND d.end_date
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

}
