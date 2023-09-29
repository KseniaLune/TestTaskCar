package com.test_task.cars.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CarUpdate {
    private String color;
    @Min(value = 0)
    @Max(value = 2_000_000, message = "The mileage is wrong")
    private Double mileage;
}
