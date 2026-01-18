package com.example.app_fast_food.bonus;

import com.example.app_fast_food.bonus.entity.Bonus;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BonusRepository extends JpaRepository<Bonus, UUID> {

    @EntityGraph(attributePaths = {
            "condition",
            "bonusProductLinks",
            "bonusProductLinks.product",
            "bonusProductLinks.product.category"
    })
    @Query("select b from Bonus b where b.active = true and :today between b.startDate and b.endDate")
    List<Bonus> findAllActiveBonusesDetails(@Param("today") LocalDate today);

    @EntityGraph(attributePaths = {
            "condition",
            "bonusProductLinks",
            "bonusProductLinks.product",
            "bonusProductLinks.product.category"
    })
    @Query("""
                select b from Bonus b
                where b.id=:id and b.active=true and
                :today between b.startDate and b.endDate
            """)

    Optional<Bonus> findBonusDetails(@Param("id") UUID id, @Param("today") LocalDate today);

    @Modifying
    @Query("update Bonus b set b.active = false where b.endDate <= :today and b.active = true")
    int deactivateAllExpired(@Param("today") LocalDate today);

}
