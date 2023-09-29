package com.test_task.cars.service;

import com.test_task.cars.domain.Car;
import com.test_task.cars.domain.CarDao;
import com.test_task.cars.entity.CarEntity;
import com.test_task.cars.controller.dto.CarUpdate;
import com.test_task.cars.controller.dto.CarMapper;
import com.test_task.cars.model.ApplicationResponse;
import com.test_task.cars.model.StatisticResponse;
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
public class CarServiceImpl implements CarService{
    private final CarDao dao;
    private final CarRepoJpa repoJpa;

    @Override
    public List<Car> getAllCars(String brand, String color, int yearOfManufacture, String country,
                                      int mileageFrom, int mileageTo,
                                      int trunkVolumeFrom, int trunkVolumeTo) {

        List<Car> carsWithFilter = dao.getCarsWithFilter(brand, color, yearOfManufacture, country,
            mileageFrom, mileageTo, trunkVolumeFrom, trunkVolumeTo);
        return carsWithFilter;
    }

    @Override
    public Car update(String numberPlate, CarUpdate carUp) {
        Optional<Car> opt = dao.getCarBy(numberPlate);
        if (opt.isEmpty()) {
            throw new IllegalArgumentException("Car with number plate" + numberPlate + "doesn't exist!");
        }
        Car car = dao.updateCar(numberPlate, carUp);
        return car;
    }


    @Override
    public ApplicationResponse addCar(Car car) {
        Optional<Car> opt = dao.getCarBy(car.getNumberPlate());
        if (opt.isPresent()) {
            throw new IllegalArgumentException("Car is present, please add new car!");
        }
        try {
            dao.save(car);
        } catch (Exception e) {
            //TODO: log add e.getMessage
            return new ApplicationResponse("Internal error, please try again!", null);
        }
        return new ApplicationResponse("The car was added", null);
    }

    @Override
    public ApplicationResponse deleteCar(String numberPlate) {
        Optional<Car> opt = dao.getCarBy(numberPlate);
        if (opt.isEmpty()) {
            throw new IllegalArgumentException("Car doesn't exist");
        }
        try {
            dao.deleteBy(numberPlate);
        } catch (Exception e) {
            //TODO: log add e.getMessage
            return new ApplicationResponse("Internal error, please try again!", null);
        }
        return new ApplicationResponse("The car was deleted", null);
    }

    @Override
    public StatisticResponse statistic() {
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

        return StatisticResponse.builder()
            .numberOfCars(cars)
            .byBrand(mapBrands)
            .byCountry(mapCountry)
            .byYear(mapYear)
            .build();
    }

}
