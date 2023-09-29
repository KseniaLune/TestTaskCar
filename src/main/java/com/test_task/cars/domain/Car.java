package com.test_task.cars.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class Car {
    private String numberPlate;
    private String brand;
    private String color;
    private Integer yearOfManufacture;
    private String country;
    private Double trunkVolume;
    private Double mileage;
}
