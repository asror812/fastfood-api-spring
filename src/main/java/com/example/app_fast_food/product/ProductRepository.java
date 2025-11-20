package com.example.app_fast_food.product;

import com.example.app_fast_food.common.repository.GenericRepository;
import com.example.app_fast_food.product.entity.Product;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends GenericRepository<Product, UUID> {

    List<Product> findProductsByCategoryName(String name);

    Optional<Product> findProductById(UUID id);

    @Query("SELECT p FROM Product p")
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
