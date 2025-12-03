package com.example.app_fast_food.check;

import com.example.app_fast_food.check.entity.Check;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
@Getter
public class CheckService {

    private final Class<Check> entityClass = Check.class;
    private final CheckRepository repository;
    private final CheckMapper mapper;

    public CheckService(CheckRepository repository, CheckMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

}
