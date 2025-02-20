package com.mustycodified.fintech_qrpayment.exception;

import com.mustycodified.fintech_qrpayment.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getAllErrors().forEach(violation -> {
            String propertyPath = violation.getCode();
            String message = violation.getDefaultMessage();
            errors.put(propertyPath, message);

        });
        return new ResponseEntity<>(new ApiResponse<>(false, "Input not Valid", errors), BAD_REQUEST);
    }



    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ApiResponse<Map<String, String>>> handleRequestPathVariableException(MethodArgumentTypeMismatchException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("parameter", String.valueOf(ex.getParameter()));
        if (ex.getValue() != null) {
            errors.put("value", ex.getValue().toString());
        }
        errors.put("requiredType", Objects.requireNonNull(ex.getRequiredType()).getSimpleName());
        String message = String.format("Request argument type mismatch. Expected: %s, Actual: %s",
                ex.getRequiredType().getSimpleName(), ex.getValue());
        return new ResponseEntity<>(new ApiResponse<>(false, message, errors), HttpStatus.BAD_REQUEST);
    }

}
