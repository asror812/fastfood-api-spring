package com.example.app_fast_food.filial.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NearestFilial extends Filial {

    private Filial nearest;
    private Double distance;

    public NearestFilial(Filial nearest, double minDistance) {
        this.nearest = nearest;
        this.distance = minDistance;
    }

}
