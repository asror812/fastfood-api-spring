package com.example.app_fast_food.bonus;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.app_fast_food.bonus.entity.BonusProductLink;

@Repository
public interface BonusProductLinkRepository extends JpaRepository<BonusProductLink, UUID> {
}
