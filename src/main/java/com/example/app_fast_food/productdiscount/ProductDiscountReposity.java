package com.example.app_fast_food.productdiscount;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDiscountReposity extends JpaRepository<ProductDiscount, UUID> {

    @Query("""
            select pd from ProductDiscount pd
            join pd.discount d
            where pd.product.id = :id
                and d.active = true
                and :today between d.startDate and d.endDate
            """)
    List<ProductDiscount> findActiveByProductId(@Param("id") UUID id, @Param("today") LocalDate today);

    @Query("""
                select pd from ProductDiscount pd
                join fetch pd.discount d
                where pd.product.id in :ids
                  and d.active = true
                  and :today between d.startDate and d.endDate
            """)
    List<ProductDiscount> findAllActiveByProductIds(
            @Param("ids") List<UUID> ids,
            @Param("today") LocalDate today);

}