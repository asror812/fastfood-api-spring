package com.example.app_fast_food.check;

import com.example.app_fast_food.check.dto.CheckResponseDTO;
import com.example.app_fast_food.check.entity.Check;
import com.example.app_fast_food.common.mapper.BaseMapper;
import com.example.app_fast_food.common.service.GenericService;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
@Getter
public class CheckService extends GenericService<Check, CheckResponseDTO> {

    private final Class<Check> entityClass = Check.class;
    private final CheckRepository repository;
    private final CheckMapper mapper;

    public CheckService(BaseMapper<Check, CheckResponseDTO> baseMapper, CheckRepository repository,
            CheckMapper mapper) {
        super(baseMapper);
        this.repository = repository;
        this.mapper = mapper;
    }

}
