package com.jakirbd.ems.exception;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
@ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ApiError> handleEmployeeNotFound(EmployeeNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiError(20002, ex.getMessage()));
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiError> handleDatabaseException(DataAccessException ex) {
        Throwable rootCause = ex.getRootCause();

        if (rootCause instanceof SQLException sqlException) {
            int errorCode = sqlException.getErrorCode();

            if (errorCode == 20001) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(new ApiError(20001, "EMPLOYEE EMAIL OR PHONE ALREADY EXISTS."));
            }

            if (errorCode == 20002) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new ApiError(20002, "EMPLOYEE NOT FOUND."));
            }

            if (errorCode == 20099) {
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiError(20099, "DATABASE PACKAGE ERROR."));
            }
        }

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(500, "DATABASE ERROR."));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .orElse("VALIDATION ERROR.");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(400, message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(500, "INTERNAL SERVER ERROR."));
    }
}
