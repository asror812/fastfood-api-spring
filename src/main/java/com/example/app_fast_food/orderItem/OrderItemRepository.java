package com.example.app_fast_food.orderItem;

import com.example.app_fast_food.common.repository.GenericRepository;
import com.example.app_fast_food.orderItem.entity.OrderItem;

import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderItemRepository extends GenericRepository<OrderItem, UUID> {

    void deleteAllByOrderId(UUID orderId);
}
