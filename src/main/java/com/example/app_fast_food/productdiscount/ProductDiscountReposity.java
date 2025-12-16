package com.example.app_fast_food.productdiscount;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.app_fast_food.productdiscount.entity.ProductDiscount;

@Repository
public interface ProductDiscountReposity extends JpaRepository<ProductDiscount, UUID> {

}