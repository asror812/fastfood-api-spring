package com.example.app_fast_food.category;

import com.example.app_fast_food.category.entity.Category;
import com.example.app_fast_food.common.repository.GenericRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends GenericRepository<Category, UUID> {
}
