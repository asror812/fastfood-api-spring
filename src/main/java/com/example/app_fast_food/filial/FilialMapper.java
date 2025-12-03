package com.example.app_fast_food.filial;

import com.example.app_fast_food.filial.dto.FilialCreateDto;
import com.example.app_fast_food.filial.dto.FilialResponseDto;
import com.example.app_fast_food.filial.dto.FilialUpdateDto;
import com.example.app_fast_food.filial.entity.Filial;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FilialMapper {

    @Mapping(target = "id", ignore = true)
    public Filial toEntity(FilialCreateDto filialCreateDTO);

    public FilialResponseDto toResponseDto(Filial filial);

    @Mapping(target = "id", ignore = true)
    public void toEntity(FilialUpdateDto filialUpdateDTO, @MappingTarget Filial filial);
}
