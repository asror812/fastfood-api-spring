package com.example.app_fast_food.bonus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.app_fast_food.bonus.entity.BonusProductLink;

@Repository
public interface BonusProductLinkRepository extends JpaRepository<BonusProductLink, UUID> {

    @Query("""
                select bp from BonusProductLink bp
                join fetch bp.bonus b
                left join fetch b.condition
                where bp.product.id = :id
                    and b.active = true
                    and :today between b.startDate and b.endDate
            """)
    List<BonusProductLink> findActiveByProductId(@Param("id") UUID id, @Param("today") LocalDate today);

    @Query("""
                select bp from BonusProductLink bp
                join fetch bp.bonus b
                left join fetch b.condition
                left join fetch b.bonusProductLinks
                left join fetch b.bonusProductLinks.product
                where bp.product.id in :ids
                    and b.active = true
                    and :today between b.startDate and b.endDate
            """)
    List<BonusProductLink> findAllActiveByProductIds(@Param("ids") List<UUID> ids, @Param("today") LocalDate today);
}
