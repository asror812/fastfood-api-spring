package com.example.app_fast_food.filial;

import com.example.app_fast_food.common.mapper.BaseMapper;
import com.example.app_fast_food.filial.dto.FilialCreateRequestTO;
import com.example.app_fast_food.filial.dto.FilialResponseDTO;
import com.example.app_fast_food.filial.dto.FilialUpdateDTO;
import com.example.app_fast_food.filial.entity.Filial;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FilialMapper extends BaseMapper<Filial, FilialResponseDTO> {

    @Mapping(target = "id", ignore = true)
    public Filial toEntity(FilialCreateRequestTO filialCreateDTO);

    @Override
    public FilialResponseDTO toResponseDTO(Filial filial);

    @Mapping(target = "id", ignore = true)
    public void toEntity(FilialUpdateDTO filialUpdateDTO, @MappingTarget Filial filial);
}
