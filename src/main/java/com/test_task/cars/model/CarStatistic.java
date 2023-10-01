package com.test_task.cars.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder(toBuilder = true)
public class CarStatistic {
    @JsonProperty("number_of_cars")
    private Integer numberOfCars ;
    @JsonProperty("number_by_brand")
    private Map<String, Integer> byBrand;
    @JsonProperty("number_by_country")
    private Map<String, Integer> byCountry;
    @JsonProperty("number_by_year")
    private Map<Integer, Integer> byYear;
}
