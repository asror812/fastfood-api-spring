package com.example.app_fast_food.product_discounts;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.app_fast_food.product_discounts.entity.ProductDiscount;

@Repository
public interface ProductDiscountReposity extends JpaRepository<ProductDiscount, UUID> {

}