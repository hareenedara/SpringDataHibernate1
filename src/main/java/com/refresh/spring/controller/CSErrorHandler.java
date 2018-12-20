package com.refresh.spring.controller;

import com.refresh.spring.model.CSError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;

@ControllerAdvice
public class CSErrorHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CSError handleException(Exception ex) {
        CSError error = new CSError();
        error.setRequestEntity(null);
        error.setErrors(new ArrayList<>());
        return error;
    }

}
