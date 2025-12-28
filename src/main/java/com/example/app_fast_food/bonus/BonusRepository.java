package com.example.app_fast_food.bonus;

import com.example.app_fast_food.bonus.entity.Bonus;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BonusRepository extends JpaRepository<Bonus, UUID> {
    @Query("SELECT b FROM Bonus b WHERE b.isActive = true AND CURRENT_DATE BETWEEN b.startDate AND b.endDate")
    List<Bonus> findAllActiveAndValid();

    @Override
    @EntityGraph(attributePaths = {
            "condition",
            "bonusProductLinks",
            "bonusProductLinks.product",
            "bonusProductLinks.product.category"
    })
    @NonNull
    List<Bonus> findAll();

    @EntityGraph(attributePaths = {
            "condition",
            "bonusProductLinks",
            "bonusProductLinks.product",
            "bonusProductLinks.product.category"
    })
    @NonNull
    Optional<Bonus> findById(@NonNull UUID id);
}
