package com.test_task.cars.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.test_task.cars.Const;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarDto {
    @NotNull(message = "Number plate must be not null")
    @NotBlank(message = "Number plate must be not blank")
    @JsonProperty("number_plate")
    @Pattern(regexp = Const.regExpPattern)
    private String numberPlate;
    @NotNull(message = "Brand plate must be not null")
    @NotBlank(message = "Brand plate must be not blank")
    private String brand;
    @NotNull(message = "Color plate must be not null")
    @NotBlank(message = "Color plate must be not blank")
    private String color;
    @Min(value = Const.minYear, message = "The car can't be older then 1950")
    @Max(value = Const.maxYear, message = "The year is wrong")
    @JsonProperty("year_of_manufacture")
    private Integer yearOfManufacture;
    @NotNull(message = "Country must be not null")
    @NotBlank(message = "Country must be not blank")
    private String country;
    @Min(value = 0)
    @Max(value = Const.maxTrunkVolume, message = "The trunk volume is wrong")
    @JsonProperty("trunk_volume")
    private Double trunkVolume;
    @Min(value = 0)
    @Max(value = Const.maxMileage, message = "The mileage is wrong")
    private Double mileage;
}
