package com.test_task.cars.model;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder(toBuilder = true)
public class StatisticResponse {
    private Integer numberOfCars ;
    private Map<String, Integer> byBrand;
    private Map<String, Integer> byCountry;
    private Map<Integer, Integer> byYear;
}
