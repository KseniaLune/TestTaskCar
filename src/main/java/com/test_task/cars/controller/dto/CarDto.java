package com.test_task.cars.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarDto {
    @NotNull(message = "Number plate must be not null")
    @NotBlank(message = "Number plate must be not blank")
    @JsonProperty("number_plate")
    @Pattern(regexp = "[A-Z]\\d{3}[A-Z]{2}\\d{2,3}")
    private String numberPlate;
    @NotNull(message = "Brand plate must be not null")
    @NotBlank(message = "Brand plate must be not blank")
    private String brand;
    @NotNull(message = "Color plate must be not null")
    @NotBlank(message = "Color plate must be not blank")
    private String color;
    @Min(value = 1950, message = "The car can't be older then 1950")
    @Max(value = 2023, message = "The year is wrong")
    @JsonProperty("year_of_manufacture")
    private Integer yearOfManufacture;
    @NotNull(message = "Country must be not null")
    @NotBlank(message = "Country must be not blank")
    private String country;
    @Min(value = 0)
    @Max(value = 3000, message = "The trunk volume is wrong")
    @JsonProperty("trunk_volume")
    private Double trunkVolume;
    @Min(value = 0)
    @Max(value = 2_000_000, message = "The mileage is wrong")
    private Double mileage;
}
