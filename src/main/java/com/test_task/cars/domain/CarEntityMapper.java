package com.test_task.cars.domain;

import com.test_task.cars.entity.CarEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarEntityMapper {
    public Car toCar(CarEntity carEntity) {
        return Car.builder()
            .numberPlate(carEntity.getNumberPlate())
            .brand(carEntity.getBrand())
            .color(carEntity.getColor())
            .yearOfManufacture(carEntity.getYearOfManufacture())
            .country(carEntity.getCountry())
            .mileage(carEntity.getMileage())
            .trunkVolume(carEntity.getTrunkVolume())
            .build();
    }

    public CarEntity toEntity(Car car) {
        return CarEntity.builder()
            .numberPlate(car.getNumberPlate())
            .brand(car.getBrand())
            .color(car.getColor())
            .yearOfManufacture(car.getYearOfManufacture())
            .country(car.getCountry())
            .mileage(car.getMileage())
            .trunkVolume(car.getTrunkVolume())
            .build();
    }

    public List<Car> toCarList(List<CarEntity> carEntities) {
        return carEntities.stream()
            .map(this::toCar)
            .collect(Collectors.toList());
    }
}
