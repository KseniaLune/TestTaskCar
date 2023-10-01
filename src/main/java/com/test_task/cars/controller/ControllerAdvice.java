package com.test_task.cars.controller;

import com.test_task.cars.model.AppError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    private static final String PATTERN = "Exception: %s, message: %s, request: %s";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AppError handlerMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
        String logMessage = String.format(PATTERN,
            ex.getClass().getName(), ex.getMessage(), request.getDescription(false));
        log.error(logMessage);

        Class<? extends MethodArgumentNotValidException> aClass = ex.getClass();
        return new AppError("Validation failed.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AppError handlerIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        String logMessage = String.format(PATTERN,
            ex.getClass().getName(),ex.getMessage(), request.getDescription(false));
        log.error(logMessage);

        return new AppError("Something went wrong!");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public AppError handleException(Exception ex, WebRequest request) {
        String logMessage = String.format(PATTERN,
            ex.getClass().getName(), ex.getMessage(), request.getDescription(false));
        log.error(logMessage);

        return new AppError("Internal error.");
    }

}
