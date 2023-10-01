package com.test_task.cars.model;

import org.springframework.data.domain.Sort;

import java.util.Arrays;

public enum SortOf {

    NUMBER_PLATE("number_plate"),
    YEAR_OF_MANUFACTURE("year_of_manufacture"),
    MILEAGE("mileage");

    public final String name;

    SortOf(String name) {
        this.name = name;
    }

    public static SortOf from(String name) {
        try {
            return Arrays.stream(SortOf.values())
                .filter((sort) -> sort.name.equals(name.toLowerCase()))
                .findFirst()
                .orElse(null);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
