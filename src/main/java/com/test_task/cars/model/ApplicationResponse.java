package com.test_task.cars.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
public class ApplicationResponse {
    private String result;
    private List<String> exceptions;
}
