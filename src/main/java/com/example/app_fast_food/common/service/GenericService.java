package com.example.app_fast_food.common.service;

import java.util.List;
import java.util.UUID;

import org.springframework.lang.NonNull;

import com.example.app_fast_food.common.mapper.BaseMapper;
import com.example.app_fast_food.common.repository.GenericRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
public abstract class GenericService<E, R> {

    protected abstract GenericRepository<E, UUID> getRepository();

    protected abstract Class<E> getEntityClass();

    protected final BaseMapper<E, R> mapper;

    public List<R> getAll() {
        return getRepository().findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public R getById(@NonNull UUID id) {
        E entity = getRepository().findById(id)
                .orElseThrow(() -> new EntityNotFoundException("%s with id: %s not found"
                        .formatted(getEntityClass().getSimpleName(), id)));
        return mapper.toResponseDTO(entity);
    }

    public void delete(UUID id) {
        E entity = getRepository().findById(id)
                .orElseThrow(() -> new EntityNotFoundException("%s with id: %s not found"
                        .formatted(getEntityClass().getSimpleName(), id)));
        getRepository().delete(entity);
    }
}
