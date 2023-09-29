package com.test_task.cars.service;

import com.test_task.cars.domain.Car;
import com.test_task.cars.entity.CarEntity;
import com.test_task.cars.controller.dto.CarDto;
import com.test_task.cars.controller.dto.CarUpdate;
import com.test_task.cars.model.ApplicationResponse;
import com.test_task.cars.model.StatisticResponse;

import java.util.List;

public interface CarService {
    List<Car> getAllCars(String brand,
                         String color,
                         int yearOfManufacture,
                         String country,
                         int mileageFrom,
                         int mileageTo,
                         int trunkVolumeFrom,
                         int trunkVolumeTo);

    ApplicationResponse addCar(Car car);

    Car update(String numberPlate, CarUpdate carUp);

    ApplicationResponse deleteCar(String numberPlate);

    StatisticResponse statistic();
}
