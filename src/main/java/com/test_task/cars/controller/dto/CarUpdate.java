package com.test_task.cars.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.test_task.cars.Const;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CarUpdate {
    private String color;
    @Min(value = 0)
    @Max(value = Const.maxMileage, message = "The mileage is wrong")
    private Double mileage;
}
