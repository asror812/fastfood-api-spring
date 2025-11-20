package com.example.app_fast_food.user.role;

import com.example.app_fast_food.common.repository.GenericRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends GenericRepository<Role, String> {

    Optional<Role> findByName(String name);
}
