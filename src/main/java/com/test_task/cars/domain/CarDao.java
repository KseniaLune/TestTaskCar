package com.test_task.cars.domain;

import com.test_task.cars.controller.dto.CarUpdate;

import java.util.List;
import java.util.Optional;

public interface CarDao {
    Optional<Car> getCarBy(String numberPlate);

    Car updateCar(String numberPlate, CarUpdate update);

    List<Car> getCarsWithFilter(String brand, String color, int yearOfManufacture, String country,
                                int mileageFrom, int mileageTo,
                                int trunkVolumeFrom, int trunkVolumeTo);

    void save(Car car);

    void deleteBy(String numberPlate);
}
