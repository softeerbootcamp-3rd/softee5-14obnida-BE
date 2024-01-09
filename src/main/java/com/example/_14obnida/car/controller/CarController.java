package com.example._14obnida.car.controller;

import com.example._14obnida.car.dto.CarRequest;
import com.example._14obnida.car.dto.CarResponse;
import com.example._14obnida.car.service.CarService;
import com.example._14obnida.common.exception.DdubukException;
import com.example._14obnida.common.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarController {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/app/car")
    public CarResponse getCost(@RequestBody CarRequest carRequest) {
        validateMapRequest(carRequest);
        return carService.getCost(carRequest);
    }

    private void validateMapRequest(CarRequest carRequest) {
        if (carRequest.getStartLongitude() == null)
            throw new DdubukException(ErrorCode.INVALID_START_GOAL_LOCATION);
        if (carRequest.getStartLatitude() == null)
            throw new DdubukException(ErrorCode.INVALID_START_GOAL_LOCATION);
        if (carRequest.getGoalLongitude() == null)
            throw new DdubukException(ErrorCode.INVALID_START_GOAL_LOCATION);
        if (carRequest.getGoalLatitude() == null)
            throw new DdubukException(ErrorCode.INVALID_START_GOAL_LOCATION);
        if (carRequest.getPublicTransportationFee() == null || carRequest.getPublicTransportationFee() < 0)
            throw new DdubukException(ErrorCode.NOT_CORRECT_PUBLIC_TRANSPORTATION_FEE);
    }
}
