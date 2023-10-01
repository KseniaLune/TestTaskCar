package com.test_task.cars.service;

import com.test_task.cars.domain.Car;
import com.test_task.cars.controller.dto.CarUpdate;
import com.test_task.cars.model.CarStatistic;
import com.test_task.cars.model.SortOf;

import java.util.List;
import java.util.Optional;

public interface CarService {
    List<Car> getAllCars(
        String brand,
        String color,
        int yearOfManufacture,
        String country,
        int mileageFrom,
        int mileageTo,
        int trunkVolumeFrom,
        int trunkVolumeTo,
        Optional<SortOf> sortParam
    );

    void addCar(Car car);

    Car update(String numberPlate, CarUpdate carUp);

    void deleteCar(String numberPlate);

    CarStatistic statistic();
}
