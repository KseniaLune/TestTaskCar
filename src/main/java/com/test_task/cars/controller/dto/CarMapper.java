package com.test_task.cars.controller.dto;

import com.test_task.cars.domain.Car;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarMapper {
    public Car toCar(CarDto dto) {
        return Car.builder()
            .numberPlate(dto.getNumberPlate())
            .brand(dto.getBrand())
            .color(dto.getColor())
            .yearOfManufacture(dto.getYearOfManufacture())
            .country(dto.getCountry())
            .mileage(dto.getMileage())
            .trunkVolume(dto.getTrunkVolume())
            .build();
    }

    public CarDto toDto(Car car) {
        return CarDto.builder()
            .numberPlate(car.getNumberPlate())
            .brand(car.getBrand())
            .color(car.getColor())
            .yearOfManufacture(car.getYearOfManufacture())
            .country(car.getCountry())
            .mileage(car.getMileage())
            .trunkVolume(car.getTrunkVolume())
            .build();
    }

    public List<CarDto> toDto(List<Car> cars) {
       return cars.stream()
            .map(e -> CarDto.builder()
                .numberPlate(e.getNumberPlate())
                .brand(e.getBrand())
                .color(e.getColor())
                .yearOfManufacture(e.getYearOfManufacture())
                .country(e.getCountry())
                .mileage(e.getMileage())
                .trunkVolume(e.getTrunkVolume())
                .build())
            .collect(Collectors.toList());
    }
}
