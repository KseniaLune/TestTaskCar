package com.test_task.cars.service;

import com.test_task.cars.controller.dto.CarUpdate;
import com.test_task.cars.domain.Car;
import com.test_task.cars.domain.CarDao;
import com.test_task.cars.model.AppError;
import com.test_task.cars.model.CarStatistic;
import com.test_task.cars.repo.CarRepoJpa;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @Mock
    private CarDao dao;
    @Mock
    private CarRepoJpa repoJpa;

    @InjectMocks
    private CarServiceImpl service;

    @Test
    void update_shouldReturnUpdateCar() {
        String numberPlate = "A";
        CarUpdate update = new CarUpdate("red", 1_000.8);

        Car car = Car.builder()
            .numberPlate(numberPlate)
            .build();
        Car updateCar = Car.builder()
            .numberPlate(numberPlate)
            .color(update.getColor())
            .mileage(update.getMileage())
            .build();

        when(dao.getCarBy(numberPlate)).thenReturn(Optional.of(car));
        when(dao.updateCar(numberPlate, update)).thenReturn(updateCar);

        Car actual = service.update(numberPlate, update);

        assertNotNull(actual);
        assertEquals(updateCar, actual);

        verify(dao, times(1)).getCarBy(numberPlate);
        verify(dao, times(1)).updateCar(numberPlate, update);
    }

    @Test
    void update_shouldThrowException_whenCarDoesNotExist() {
        String numberPlate = "A";
        CarUpdate update = new CarUpdate("red", 1_000.8);
        when(dao.getCarBy(numberPlate)).thenReturn(Optional.empty());

        assertThrowsExactly(IllegalArgumentException.class, () -> service.update(numberPlate, update));
        verify(dao, times(1)).getCarBy(numberPlate);
        verify(dao, never()).updateCar(numberPlate, update);
    }

    @Test
    void addCar_shouldReturnResponse_whenCarWasSave() {
        Car car = Car.builder()
            .numberPlate("A")
            .build();
        AppError response = new AppError("The car was added");

        when(dao.getCarBy(car.getNumberPlate())).thenReturn(Optional.empty());
        doNothing().when(dao).save(car);

        service.addCar(car);

        verify(dao, times(1)).getCarBy(car.getNumberPlate());
        verify(dao, times(1)).save(car);
    }

    @Test
    void addCar_shouldThrowException_whenCarAlreadyExist() {
        Car car = Car.builder()
            .numberPlate("A")
            .build();
        when(dao.getCarBy(car.getNumberPlate())).thenReturn(Optional.of(car));

        assertThrowsExactly(IllegalArgumentException.class, () -> service.addCar(car));

        verify(dao, times(1)).getCarBy(car.getNumberPlate());
        verify(dao, never()).save(car);
    }

    @Test
    void addCar_shouldReturnResponse_whenCarWasDelete() {
        Car car = Car.builder()
            .numberPlate("A")
            .build();
        AppError response = new AppError("The car was deleted");

        when(dao.getCarBy(car.getNumberPlate())).thenReturn(Optional.of(car));
        doNothing().when(dao).deleteBy(car.getNumberPlate());

        service.deleteCar(car.getNumberPlate());

        verify(dao, times(1)).getCarBy(car.getNumberPlate());
        verify(dao, times(1)).deleteBy(car.getNumberPlate());
    }

    @Test
    void deleteCar_shouldThrowException_whenCarDoesNotExist() {
        String numberPlate = "A";

        when(dao.getCarBy(numberPlate)).thenReturn(Optional.empty());

        assertThrowsExactly(IllegalArgumentException.class, () -> service.deleteCar(numberPlate));
        verify(dao, times(1)).getCarBy(numberPlate);
        verify(dao, never()).deleteBy(numberPlate);
    }

    @Test
    void statistic_returnValidResponse_whenThereIsAllData() {
        List<String> listBrands = List.of("Lada,1", "Opel,3");
        List<String> listCountry = List.of("Russia,4");
        List<String> listYear = List.of("1995,3", "2007,1");

        HashMap<String, Integer> mapBrands = new HashMap<>();
        HashMap<String, Integer> mapCountry = new HashMap<>();
        HashMap<Integer, Integer> mapYear = new HashMap<>();

        mapBrands.put("Lada", 1);
        mapBrands.put("Opel", 3);
        mapCountry.put("Russia", 4);
        mapYear.put(1995, 3);
        mapYear.put(2007, 1);

        CarStatistic response = CarStatistic.builder()
            .numberOfCars(4)
            .byBrand(mapBrands)
            .byCountry(mapCountry)
            .byYear(mapYear)
            .build();

        when(repoJpa.countCarsByBrands()).thenReturn(listBrands);
        when(repoJpa.countCarsByCountry()).thenReturn(listCountry);
        when(repoJpa.countCarsByYear()).thenReturn(listYear);
        when(repoJpa.countAllCars()).thenReturn(4);

        CarStatistic actual = service.statistic();

        assertNotNull(actual);
        assertEquals(response, actual);

        verify(repoJpa, times(1)).countCarsByBrands();
        verify(repoJpa, times(1)).countCarsByCountry();
        verify(repoJpa, times(1)).countCarsByYear();
        verify(repoJpa, times(1)).countAllCars();

    }
    @Test
    void statistic_returnValidResponse_whenDataBaseEmpty() {
        HashMap<String, Integer> mapBrands = new HashMap<>();
        HashMap<String, Integer> mapCountry = new HashMap<>();
        HashMap<Integer, Integer> mapYear = new HashMap<>();

        CarStatistic response = CarStatistic.builder()
            .numberOfCars(0)
            .byBrand(mapBrands)
            .byCountry(mapCountry)
            .byYear(mapYear)
            .build();

        when(repoJpa.countCarsByBrands()).thenReturn(new ArrayList<>());
        when(repoJpa.countCarsByCountry()).thenReturn(new ArrayList<>());
        when(repoJpa.countCarsByYear()).thenReturn(new ArrayList<>());
        when(repoJpa.countAllCars()).thenReturn(0);

        CarStatistic actual = service.statistic();

        assertNotNull(actual);
        assertEquals(response, actual);

        verify(repoJpa, times(1)).countCarsByBrands();
        verify(repoJpa, times(1)).countCarsByCountry();
        verify(repoJpa, times(1)).countCarsByYear();
        verify(repoJpa, times(1)).countAllCars();

    }
}