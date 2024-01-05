package com.example._14obnida.car.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CarResponse {
    private int distance; //meter or kelometer
    private int duration; //millisecond or minute
    private int tollFare;
    private int fuelPrice;
}