package com.example.app_fast_food.bonus;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.app_fast_food.bonus.entity.BonusCondition;

public interface BonusConditionRepository extends JpaRepository<BonusCondition, UUID> {

}
