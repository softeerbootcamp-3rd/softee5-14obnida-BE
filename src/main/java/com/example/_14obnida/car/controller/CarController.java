package com.example._14obnida.car.controller;

import com.example._14obnida.car.dto.CarRequest;
import com.example._14obnida.car.dto.CarResponse;
import com.example._14obnida.car.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

//@RestController
@Controller
public class CarController {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/app/car")
    @ResponseBody
    public CarResponse getResponse(@RequestBody CarRequest carRequest) {
        validateMapRequest(carRequest);
        return carService.getResponse(carRequest);
    }

    private void validateMapRequest(CarRequest carRequest) {
        if (carRequest.getStartLongitude() == null)
            throw new RuntimeException(); //Exception 클래스 구현 예정
        if (carRequest.getStartLatitude() == null)
            throw new RuntimeException();
        if (carRequest.getGoalLongitude() == null)
            throw new RuntimeException();
        if (carRequest.getGoalLatitude() == null)
            throw new RuntimeException();
        if (carRequest.getPublicTransportationFee() == null)
            throw new RuntimeException();
    }
}