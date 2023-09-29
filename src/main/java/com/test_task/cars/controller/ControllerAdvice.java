package com.test_task.cars.controller;

import com.test_task.cars.model.ApplicationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApplicationResponse handlerMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<String> list = fieldErrors.stream()
            .map(FieldError::getDefaultMessage)
            .toList();

        return new ApplicationResponse("Validation failed", list);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApplicationResponse handlerIllegalArgumentException(IllegalArgumentException ex) {

        return new ApplicationResponse("Something went wrong!", List.of(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApplicationResponse handleException(Exception ex){
        //TODO:log
        ex.printStackTrace();
        return new ApplicationResponse("Internal error.", null);
    }

}
