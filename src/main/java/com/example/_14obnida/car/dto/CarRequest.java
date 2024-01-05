package com.example._14obnida.car.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CarRequest {
    private Double startLongitude;
    private Double startLatitude;
    private Double goalLongitude;
    private Double goalLatitude;
}