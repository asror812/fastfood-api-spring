package com.example.app_fast_food.order;

import com.example.app_fast_food.order.entity.Order;
import com.example.app_fast_food.order.entity.OrderStatus;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Query("select o from Order o left join fetch o.orderItems where o.user.id = :userId and o.status = 'BASKET'")
    Optional<Order> findBasketByUserId(@Param("userId") UUID userId);

    @Query("select o from Order o left join fetch o.orderItems where o.user.phoneNumber = :phoneNumber and o.status = 'BASKET'")
    Optional<Order> findBasketByUserPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @EntityGraph(attributePaths = {
            "orderItems",
            "orderItems.product"
    })
    @Query("select distinct o from Order o where o.status = :status")
    List<Order> findByStatus(@Param("status") OrderStatus status);

    Optional<Order> findOrderById(UUID orderId);

    @Query("select count(o) from Order o where o.user.id = :userId and o.status = 'TAKEN'")
    int getPurchasesCountOfUser(@Param("userId") UUID userId);
}
