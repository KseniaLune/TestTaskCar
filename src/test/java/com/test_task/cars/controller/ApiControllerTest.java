package com.test_task.cars.controller;

import com.test_task.cars.controller.dto.CarDto;
import com.test_task.cars.controller.dto.CarMapper;
import com.test_task.cars.domain.Car;
import com.test_task.cars.model.AppError;
import com.test_task.cars.model.SortOf;
import com.test_task.cars.service.CarService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApiControllerTest {

    @Mock
    private CarService service;
    @Mock
    private CarMapper mapper;

    @InjectMocks
    private ApiController controller;


    @Test
    void getAllCars_withoutFiltersAndSort() {
        List<Car> cars = getCars();
        when(service.getAllCars(null, null, 0, null,
            0, 0, 0, 0, Optional.empty())).thenReturn(cars);

        List<CarDto> dto = toDto(cars);
        when(mapper.toDto(cars)).thenReturn(dto);

        ResponseEntity<List<CarDto>> actual = controller.getAllCars(
            null, null, null, null,
            null, null,
            null, null, null);

        assertNotNull(actual);
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, actual.getHeaders().getContentType());
        assertEquals(dto, actual.getBody());

        verify(service).getAllCars(null, null, 0, null,
            0, 0, 0, 0, Optional.empty());
        verify(mapper).toDto(cars);
    }

    @Test
    void getAllCars_withAllFiltersAndSort() {
        List<Car> cars = getCars();
        Car car = cars.get(0);
        List<Car> serviceResult = List.of(car);

        String brand = car.getBrand();
        String color = car.getColor();
        Integer yearOfManufacture = car.getYearOfManufacture();
        String country = car.getCountry();
        Integer mileageFrom = car.getMileage().intValue() - 1;
        Integer mileageTo = car.getMileage().intValue() + 1;
        Integer trunkVolumeFrom = car.getTrunkVolume().intValue() - 1;
        Integer trunkVolumeTo = car.getTrunkVolume().intValue() + 1;

        when(service.getAllCars(
            brand, color, yearOfManufacture, country,
            mileageFrom, mileageTo, trunkVolumeFrom, trunkVolumeTo,
            Optional.of(SortOf.MILEAGE)
        )).thenReturn(serviceResult);

        List<CarDto> dto = toDto(serviceResult);
        when(mapper.toDto(serviceResult)).thenReturn(dto);


        ResponseEntity<List<CarDto>> actual = controller.getAllCars(
            brand, color, yearOfManufacture, country,
            mileageFrom, mileageTo, trunkVolumeFrom, trunkVolumeTo, "MILEAGE");

        assertNotNull(actual);
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, actual.getHeaders().getContentType());
        assertEquals(dto, actual.getBody());

        verify(service).getAllCars(brand, color, yearOfManufacture, country,
            mileageFrom, mileageTo, trunkVolumeFrom, trunkVolumeTo, Optional.of(SortOf.MILEAGE));
        verify(mapper).toDto(serviceResult);
    }

    @Test
    void getAllCars_withSomeFilters() {
        String color = "red";
        Integer trunkVolumeFrom = null;
        Integer trunkVolumeTo = 200;

        List<Car> serviceResult = getCars().stream()
            .filter(e -> e.getColor().equals(color) && e.getTrunkVolume() <= trunkVolumeTo)
            .collect(Collectors.toList());


        when(service.getAllCars(
            null, color, 0, null,
            0, 0, 0, trunkVolumeTo, Optional.empty()
        )).thenReturn(serviceResult);

        List<CarDto> dto = toDto(serviceResult);
        when(mapper.toDto(serviceResult)).thenReturn(dto);


        ResponseEntity<List<CarDto>> actual = controller.getAllCars(
            null, color, 0, null,
            0, 0, trunkVolumeFrom, trunkVolumeTo, null);

        assertNotNull(actual);
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, actual.getHeaders().getContentType());
        assertEquals(dto, actual.getBody());

        verify(service).getAllCars(null, color, 0, null,
            0, 0, 0, trunkVolumeTo, Optional.empty());
        verify(mapper).toDto(serviceResult);
    }

    @Test
    void addCar_success() {
        Car car = Car.builder()
            .numberPlate("A111AA99")
            .brand("Lada")
            .color("red")
            .yearOfManufacture(1991)
            .country("Russia")
            .mileage(1_000.5)
            .trunkVolume(45.5)
            .build();
        CarDto dto = CarDto.builder()
            .numberPlate("A111AA99")
            .brand("Lada")
            .color("red")
            .yearOfManufacture(1991)
            .country("Russia")
            .mileage(1_000.5)
            .trunkVolume(45.5)
            .build();

        when(mapper.toCar(dto)).thenReturn(car);
        doNothing().when(service).addCar(car);
        ResponseEntity<AppError> actual = controller.addCar(dto);

        assertNotNull(actual);
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, actual.getHeaders().getContentType());

        verify(mapper).toCar(dto);
        verify(service).addCar(car);
    }

    @Test
    void addCar_exception_theCarIsPresent() {
        Car car = Car.builder()
            .numberPlate("A111AA99")
            .brand("Lada")
            .color("red")
            .yearOfManufacture(1991)
            .country("Russia")
            .mileage(1_000.5)
            .trunkVolume(45.5)
            .build();
        CarDto dto = CarDto.builder()
            .numberPlate("A111AA99")
            .brand("Lada")
            .color("red")
            .yearOfManufacture(1991)
            .country("Russia")
            .mileage(1_000.5)
            .trunkVolume(45.5)
            .build();

        when(mapper.toCar(dto)).thenReturn(car);
        doThrow(new IllegalArgumentException("Car is present, please add new car!")).when(service).addCar(car);

        assertThrowsExactly(IllegalArgumentException.class, () -> controller.addCar(dto));

        verify(mapper).toCar(dto);
        verify(service).addCar(car);
    }

    @Test
    void addCar_dtoValidation() {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        CarDto dto = CarDto.builder().build();
        Set<ConstraintViolation<CarDto>> validate = validator.validate(dto);

        assertFalse(validate.isEmpty());
    }

    private List<Car> getCars() {
        Car one = Car.builder()
            .numberPlate("A111AA99")
            .brand("Lada")
            .color("red")
            .yearOfManufacture(1991)
            .country("Russia")
            .mileage(1_000.5)
            .trunkVolume(45.5)
            .build();
        Car two = Car.builder()
            .numberPlate("A112AA99")
            .brand("Lada")
            .color("blue")
            .yearOfManufacture(1992)
            .country("Russia")
            .mileage(0.0)
            .trunkVolume(200.3)
            .build();
        Car three = Car.builder()
            .numberPlate("A113AA99")
            .brand("Volga")
            .color("red")
            .yearOfManufacture(1993)
            .country("Germany")
            .mileage(5_000.8)
            .trunkVolume(150.6)
            .build();
        return List.of(one, two, three);
    }

    private List<CarDto> toDto(List<Car> cars) {
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