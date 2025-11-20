package com.example.app_fast_food.user.permission;


import com.example.app_fast_food.common.repository.GenericRepository;
import com.example.app_fast_food.user.permission.entity.Permission;

import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface PermissionRepository extends GenericRepository<Permission , UUID> {
       Set<Permission> findAllByNameIn(Set<String> names);
}
