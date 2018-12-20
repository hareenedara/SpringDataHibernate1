package com.refresh.spring.model;

import lombok.Data;
import org.springframework.http.RequestEntity;

import java.util.List;

@Data
public class CSError {
    Exception exception;
    RequestEntity requestEntity;
    List<String> errors;
}
