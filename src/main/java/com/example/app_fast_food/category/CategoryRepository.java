package com.example.app_fast_food.category;

import com.example.app_fast_food.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    Optional<Category> findByName(String name);

    @Query(value = """
            SELECT c.* FROM categories c WHERE c.parent_id IS NULL
            """, nativeQuery = true)
    List<Category> getParentCategories();

}
