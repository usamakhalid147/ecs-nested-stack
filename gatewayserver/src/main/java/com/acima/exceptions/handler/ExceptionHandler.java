package com.acima.exceptions.handler;

import com.acima.exceptions.exception.GenericException;
import com.acima.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = GenericException.class)
    public ResponseEntity<Object> handleGenericException(GenericException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(new ApiResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage().replace("Failed for [400]:","").strip(),
                null));
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String,String> errors = new HashMap<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField() , error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.put(error.getObjectName() , error.getDefaultMessage());
        }
        final ApiResponse apiError = new ApiResponse(HttpStatus.BAD_REQUEST.value(), "Please provide all required fields", Map.of("errors",errors));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(apiError);
    }


}
