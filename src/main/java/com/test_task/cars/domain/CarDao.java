package com.test_task.cars.domain;

import com.test_task.cars.controller.dto.CarUpdate;
import com.test_task.cars.model.SortOf;
import lombok.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

public interface CarDao {
    Optional<Car> getCarBy(String numberPlate);

    Car updateCar(String numberPlate, CarUpdate update);

    List<Car> getCarsWithFilter(
        String brand, String color, int yearOfManufacture, String country,
        int mileageFrom, int mileageTo,
        int trunkVolumeFrom, int trunkVolumeTo, Optional<SortOf> sortParam
    );

    void save(Car car);

    void deleteBy(String numberPlate);
}
