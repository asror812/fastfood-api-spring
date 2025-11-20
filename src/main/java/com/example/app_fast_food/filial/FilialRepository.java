package com.example.app_fast_food.filial;

import com.example.app_fast_food.common.repository.GenericRepository;
import com.example.app_fast_food.filial.entity.Filial;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface FilialRepository extends GenericRepository<Filial, UUID> {


}
