package com.example.app_fast_food.check;

import com.example.app_fast_food.check.entity.Check;
import com.example.app_fast_food.common.repository.GenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CheckRepository extends GenericRepository<Check, UUID> {

    @Query("SELECT COUNT(c) FROM Check c WHERE c.user.id =: userId")
    int getPurchasesCountOfUser(UUID userId);
}
