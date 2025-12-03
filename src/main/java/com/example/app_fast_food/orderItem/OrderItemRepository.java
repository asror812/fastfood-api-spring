package com.example.app_fast_food.orderItem;

import com.example.app_fast_food.orderItem.entity.OrderItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {

    void deleteAllByOrderId(UUID orderId);
}
