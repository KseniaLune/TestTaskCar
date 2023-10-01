package com.test_task.cars.domain;

import com.test_task.cars.controller.dto.CarUpdate;
import com.test_task.cars.entity.CarEntity;
import com.test_task.cars.model.SortOf;
import com.test_task.cars.repo.CarRepoJpa;
import com.test_task.cars.repo.CarRepoHibernate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CarDaoImpl implements CarDao {
    private final CarRepoJpa repoJpa;
    private final CarRepoHibernate repoHibernate;
    private final CarEntityMapper mapper;

    @Override
    public Optional<Car> getCarBy(String numberPlate) {
        Optional<CarEntity> opt = repoJpa.findById(numberPlate);
        if (opt.isEmpty()) {
            return Optional.empty();
        }
        Car car = mapper.toCar(opt.get());
        return Optional.of(car);
    }

    @Override
    public List<Car> getCarsWithFilter(
        String brand, String color, int yearOfManufacture, String country,
        int mileageFrom, int mileageTo,
        int trunkVolumeFrom, int trunkVolumeTo, Optional<SortOf> sortParam
    ) {
        List<CarEntity> carsWithFilter = repoHibernate.getCarsWithFilter(
            brand, color, yearOfManufacture, country,
            mileageFrom, mileageTo, trunkVolumeFrom, trunkVolumeTo, sortParam);
        return mapper.toCarList(carsWithFilter);
    }

    @Override
    @Transactional
    public void save(Car car) {
        CarEntity entity = mapper.toEntity(car);
        entity.setTime(LocalDateTime.now());
        repoJpa.save(entity);
    }

    @Override
    @Transactional
    public Car updateCar(String numberPlate, CarUpdate update) {
        repoJpa.updateCar(numberPlate, update.getColor(), update.getMileage());
        Optional<CarEntity> carEntity = repoJpa.findById(numberPlate);
        return mapper.toCar(carEntity.orElseThrow());
    }

    @Override
    @Transactional
    public void deleteBy(String numberPlate) {
        repoJpa.deleteById(numberPlate);
    }
}
