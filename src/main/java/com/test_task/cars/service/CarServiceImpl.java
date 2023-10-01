package com.test_task.cars.service;

import com.test_task.cars.domain.Car;
import com.test_task.cars.domain.CarDao;
import com.test_task.cars.controller.dto.CarUpdate;
import com.test_task.cars.model.CarStatistic;
import com.test_task.cars.model.SortOf;
import com.test_task.cars.repo.CarRepoJpa;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CarServiceImpl implements CarService {
    private final CarDao dao;
    private final CarRepoJpa repoJpa;

    @Override
    public List<Car> getAllCars(
        String brand, String color, int yearOfManufacture, String country,
        int mileageFrom, int mileageTo,
        int trunkVolumeFrom, int trunkVolumeTo, Optional<SortOf> sortParam
    ) {
        return dao.getCarsWithFilter(
            brand, color, yearOfManufacture, country,
            mileageFrom, mileageTo, trunkVolumeFrom, trunkVolumeTo, sortParam
        );
    }

    @Override
    public Car update(String numberPlate, CarUpdate carUp) {
        Optional<Car> opt = dao.getCarBy(numberPlate);
        if (opt.isEmpty()) {
            throw new IllegalArgumentException("Car with number plate" + numberPlate + "doesn't exist!");
        }

        return dao.updateCar(numberPlate, carUp);
    }

    @Override
    public void addCar(Car car) {
        Optional<Car> opt = dao.getCarBy(car.getNumberPlate());
        if (opt.isPresent()) {
            throw new IllegalArgumentException("Car is present, please add new car!");
        }

        dao.save(car);
    }

    @Override
    public void deleteCar(String numberPlate) {
        Optional<Car> opt = dao.getCarBy(numberPlate);
        if (opt.isEmpty()) {
            throw new IllegalArgumentException("Car doesn't exist");
        }

        dao.deleteBy(numberPlate);
    }

    @Override
    public CarStatistic statistic() {
        HashMap<String, Integer> mapBrands = new HashMap<>();

        List<String> listBrands = repoJpa.countCarsByBrands();
        for (String s : listBrands) {
            String[] split = s.split(",");
            mapBrands.put(split[0], Integer.valueOf(split[1]));
        }

        HashMap<String, Integer> mapCountry = new HashMap<>();
        List<String> listCountry = repoJpa.countCarsByCountry();
        for (String s : listCountry) {
            String[] split = s.split(",");
            mapCountry.put(split[0], Integer.valueOf(split[1]));
        }

        HashMap<Integer, Integer> mapYear = new HashMap<>();
        List<String> listYear = repoJpa.countCarsByYear();
        for (String s : listYear) {
            String[] split = s.split(",");
            mapYear.put(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
        }

        int cars = repoJpa.countAllCars();

        return CarStatistic.builder()
            .numberOfCars(cars)
            .byBrand(mapBrands)
            .byCountry(mapCountry)
            .byYear(mapYear)
            .build();
    }
}
