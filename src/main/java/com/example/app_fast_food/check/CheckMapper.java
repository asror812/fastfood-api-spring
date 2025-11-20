package com.example.app_fast_food.check;

import com.example.app_fast_food.check.dto.CheckCreateRequestDTO;
import com.example.app_fast_food.check.dto.CheckResponseDTO;
import com.example.app_fast_food.check.dto.CheckUpdateRequestDTO;
import com.example.app_fast_food.check.entity.Check;
import com.example.app_fast_food.common.mapper.BaseMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CheckMapper extends BaseMapper<Check, CheckResponseDTO> {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "filial", ignore = true)
    @Mapping(target = "courier", ignore = true)
    @Mapping(target = "order.id", source = "orderId")
    @Mapping(target = "user.id", source = "userId")
    public Check toEntity(CheckCreateRequestDTO checkCreateDTO);

    @Override
    public CheckResponseDTO toResponseDTO(Check check);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "filial", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "user", ignore = true)
    public void toEntity(CheckUpdateRequestDTO checkUpdateDTO, @MappingTarget Check check);
}
