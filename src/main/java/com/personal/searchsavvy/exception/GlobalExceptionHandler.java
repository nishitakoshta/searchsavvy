package com.personal.searchsavvy.exception;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<Map<String, Object>> handleConstraintViolationException(ConstraintViolationException ex) {
        List<Map<String, Object>> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            Map<String, Object> errorMessage = new HashMap<>();
            String propertyPath = violation.getPropertyPath().toString();
            errorMessage.put("Error", "Constraint violation: " + propertyPath + " " + violation.getMessage());
            errors.add(errorMessage);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("errors", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        Map<String, Object> message = new HashMap<>();
        // Extract the relevant part of the exception message
        String constraintMessage = ex.getCause().getMessage().split("constraint")[0].trim();
        // Use a user-friendly message without revealing sensitive database details
        message.put("Error", "Validation error: " + constraintMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<Map<String, Object>> handleEntityNotFoundException(EntityNotFoundException ex) {
        Map<String, Object> message = new HashMap<>();
        message.put("Error","The entity with the given id was not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ResponseEntity<Map<String, Object>> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex, HttpServletRequest request) {
        Map<String, Object> message = new HashMap<>();
        message.put("Error", "Required request parameter '" + ex.getParameterName() + "' for method parameter type " + ex.getParameterType() + " is not present.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
    @ExceptionHandler(value = {InvalidDataAccessResourceUsageException.class})
    public ResponseEntity<Map<String, Object>> handleInvalidDataAccessResourceUsageException(InvalidDataAccessResourceUsageException ex) {
        Map<String, Object> message = new HashMap<>();
        // Extract the specific cause of the exception
        Throwable rootCause = ExceptionUtils.getRootCause(ex);
        // Modify the error message based on the specific cause
        if (rootCause != null) {
            String errorMessage = rootCause.getMessage();
            message.put("Error", "Invalid data access resource usage: " + errorMessage);
        } else {
            message.put("Error", "Invalid data access resource usage");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }
}
