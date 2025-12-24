package com.example.app_fast_food.order;

import com.example.app_fast_food.order.entity.Order;
import com.example.app_fast_food.order.entity.OrderStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.orderItems WHERE o.user.id = :userId AND o.status = 'BASKET'")
    Optional<Order> findBasketByUserId(@Param("userId") UUID userId);

    List<Order> findByStatus(OrderStatus status);

    Optional<Order> findOrderById(UUID orderId);

    void deleteOrderByUserIdAndStatus(UUID userId, OrderStatus status);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.user.id = :userId AND o.status = 'TAKEN'")
    int getPurchasesCountOfUser(@Param("userId") UUID userId);
}
