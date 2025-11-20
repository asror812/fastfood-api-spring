package com.example.app_fast_food.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepository<ENTITY , ID> extends JpaRepository<ENTITY , ID > {

}
