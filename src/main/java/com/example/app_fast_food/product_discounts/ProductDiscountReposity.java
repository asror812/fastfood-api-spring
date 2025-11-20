package com.example.app_fast_food.product_discounts;

import java.util.UUID;
import org.springframework.stereotype.Repository;

import com.example.app_fast_food.common.repository.GenericRepository;

@Repository
public interface ProductDiscountReposity extends GenericRepository<ProductDiscount, UUID> {

}