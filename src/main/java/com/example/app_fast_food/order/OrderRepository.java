package com.example.app_fast_food.order;

import com.example.app_fast_food.order.entity.Order;
import com.example.app_fast_food.order.entity.OrderStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query("SELECT o FROM Order o WHERE o.user.id =: userId AND o.orderStatus = 'BASKET'")
    Optional<Order> findBasketByUserId(UUID userId);

    List<Order> findByOrderStatus(OrderStatus orderStatus);

    Optional<Order> findOrderById(UUID orderId);

    void deleteOrderByUserIdAndOrderStatus(UUID userId, OrderStatus orderStatus);

}
