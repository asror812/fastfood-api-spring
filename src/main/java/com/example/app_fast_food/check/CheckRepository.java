package com.example.app_fast_food.check;

import com.example.app_fast_food.check.entity.Check;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CheckRepository extends JpaRepository<Check, UUID> {

    @Query("SELECT COUNT(c) FROM Check c WHERE c.user.id =: userId")
    int getPurchasesCountOfUser(UUID userId);
}
