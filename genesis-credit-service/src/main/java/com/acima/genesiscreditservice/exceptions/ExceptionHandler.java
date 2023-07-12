package com.acima.genesiscreditservice.exceptions;

import com.acima.genesiscreditservice.model.ErrorResponse;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Exception handler.
 *
 * @author LavKumar
 */
@RestControllerAdvice
public class ExceptionHandler {

    private final MessageSource messageSource;

    /**
     * Instantiates a new Exception handler.
     *
     * @param messageSource the message source
     */
    public ExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Handle generic exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = GenericException.class)
    public ResponseEntity<Object> handleGenericException(GenericException ex, WebRequest request) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(
                new ErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        new Date(),
                        "Bad Request", ex.getMessage().replace("Failed for [400]:", "").strip())
        );
    }


    /**
     * Handle method argument not valid response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, final WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.put(error.getObjectName(), error.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(
                new ErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        new Date(),
                        "Please provide all required fields", Map.of("errors", errors))
        );
    }

    /**
     * Handle constraint violation exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolationException(
            ConstraintViolationException ex, WebRequest request) {
        Map<String, Object> errors = new HashMap<>();
        if (!ex.getConstraintViolations().isEmpty()) {
            for (ConstraintViolation constraintViolation : ex.getConstraintViolations()) {
                String fieldName = null;
                for (Path.Node node : constraintViolation.getPropertyPath()) {
                    fieldName = node.getName();
                }
                if (constraintViolation.getMessage().startsWith("{")) {
                    String replaceBraces = constraintViolation.getMessage().replace("{", "").replace("}", "");
                    errors.put(fieldName, messageSource.getMessage(replaceBraces,
                            null, null));
                } else {
                    errors.put(fieldName, constraintViolation.getMessage());
                }

            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(
                new ErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        new Date(),
                        "Please provide all required fields", Map.of("errors", errors))
        );
    }

    /**
     * Handle missing servlet request parameter exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ResponseEntity<Object> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException ex, WebRequest request) {
        Map<String, Object> errors = new HashMap<>();
        errors.put(ex.getParameterName(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(
                new ErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        new Date(),
                        "Please provide missing fields", Map.of("errors", errors))
        );
    }

    /**
     * Handle http message not readable response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, WebRequest request) {
        Map<String, Object> errors = new HashMap<>();
        if (ex.getCause() instanceof MismatchedInputException) {
            MismatchedInputException mismatchedInputException = (MismatchedInputException) ex.getCause();
            mismatchedInputException.getPath().forEach(reference -> {
                if (mismatchedInputException.getOriginalMessage().contains("java.time.LocalDate")) {
                    errors.put(reference.getFieldName(), "please provide date in format (yyyy-MM-dd)");
                } else {
                    errors.put(reference.getFieldName(), mismatchedInputException.getOriginalMessage());
                }

            });
        }

        if (ex.getCause() instanceof JsonMappingException) {
            JsonMappingException jsonMappingException = (JsonMappingException) ex.getCause();
            jsonMappingException.getPath().forEach(reference -> {
                        if (jsonMappingException.getOriginalMessage().contains("java.time.LocalDate")) {
                            errors.put(reference.getFieldName(), "please provide date in format (yyyy-MM-dd)");
                        } else {
                            errors.put(reference.getFieldName(), jsonMappingException.getOriginalMessage());
                        }

                    }
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(
                new ErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        new Date(),
                        "Please provide valid values for fields", Map.of("errors", errors))
        );
    }

}
