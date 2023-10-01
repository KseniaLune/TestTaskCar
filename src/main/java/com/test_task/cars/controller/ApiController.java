package com.test_task.cars.controller;

import com.test_task.cars.domain.Car;
import com.test_task.cars.controller.dto.CarDto;
import com.test_task.cars.controller.dto.CarUpdate;
import com.test_task.cars.controller.dto.CarMapper;
import com.test_task.cars.model.SortOf;
import com.test_task.cars.service.CarService;
import com.test_task.cars.model.AppError;
import com.test_task.cars.model.CarStatistic;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/car")
@RequiredArgsConstructor
public class ApiController {
    private final CarService service;
    private final CarMapper carMapper;

    @GetMapping
    public ResponseEntity<List<CarDto>> getAllCars(
        @RequestParam(required = false, name = "brand") String brand,
        @RequestParam(required = false, name = "color") String color,
        @RequestParam(required = false, name = "year_of_manufacture") Integer yearOfManufacture,
        @RequestParam(required = false, name = "country") String country,
        @RequestParam(required = false, name = "mileage_from") Integer mileageFrom,
        @RequestParam(required = false, name = "mileage_to") Integer mileageTo,
        @RequestParam(required = false, name = "trunk_volume_from") Integer trunkVolumeFrom,
        @RequestParam(required = false, name = "trunk_volume_to") Integer trunkVolumeTo,
        @RequestParam(required = false, name = "sort_by") String sortParam
    ) {
        int year = 0, mileageF = 0, mileageT = 0, trunkVolF = 0, trunkVolT = 0;

        if (yearOfManufacture != null) {
            year = yearOfManufacture;
        }
        if (mileageFrom != null) {
            mileageF = mileageFrom;
        }
        if (mileageTo != null) {
            mileageT = mileageTo;
        }
        if (trunkVolumeFrom != null) {
            trunkVolF = trunkVolumeFrom;
        }
        if (trunkVolumeTo != null) {
            trunkVolT = trunkVolumeTo;
        }

        Optional<SortOf> sort;
        if (sortParam != null) {
            SortOf sortOf = SortOf.from(sortParam);
            if (sortOf == null) {
                throw new IllegalArgumentException("You can't sort by " + sortParam);
            }

            sort = Optional.of(sortOf);
        } else {
            sort = Optional.empty();
        }
        List<Car> allCars = service.getAllCars(brand, color, year, country, mileageF, mileageT, trunkVolF, trunkVolT, sort);
        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(carMapper.toDto(allCars));
    }

    @PostMapping("/add")
    public ResponseEntity<AppError> addCar(@Valid @RequestBody CarDto dto) {
       service.addCar(carMapper.toCar(dto));
        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .build();
    }

    @PutMapping("/{number_plate}")
    public ResponseEntity<CarDto> updateCar(
        @PathVariable("number_plate") String numberPlate,
        @Valid @RequestBody CarUpdate carUp
    ) {
        Car newCar = service.update(numberPlate, carUp);
        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(carMapper.toDto(newCar));
    }

    @DeleteMapping("/{number_plate}")
    public ResponseEntity<AppError> deleteCar(@PathVariable("number_plate") String numberPlate) {
        service.deleteCar(numberPlate);
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .build();
    }

    @GetMapping("/statistic")
    public ResponseEntity<CarStatistic> getStatistic() {
        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(service.statistic());
    }
}
