package org.stephen.ledgerflow.common.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.stephen.ledgerflow.common.api.ApiError;
import org.stephen.ledgerflow.common.api.FieldValidationError;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){

        List<FieldValidationError> fieldErrors = new ArrayList<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()){
            fieldErrors.add(
                    new FieldValidationError(
                            fieldError.getField(),
                            fieldError.getDefaultMessage()
                    )
            );
        }

        ApiError apiError = new ApiError(
                Instant.now(),
                400,
                "VALIDATION_FAILED",
                "Request validation failed",
                fieldErrors
        );

        return ResponseEntity.badRequest().body(apiError);

    }

    @ExceptionHandler(DuplicateDepartmentCodeException.class)
    public ResponseEntity<ApiError> handleDuplicateDepartmentCodeException(DuplicateDepartmentCodeException ex){
        ApiError apiError = new ApiError(
                Instant.now(),
                409,
                "DEPARTMENT_CODE_ALREADY_EXISTS",
                ex.getMessage(),
                List.of()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }
}
