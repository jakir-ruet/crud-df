package com.jakirbd.ems.exception;

import java.time.LocalDateTime;

public class ApiError {
	private LocalDateTime timestamp;
    private int errorCode;
    private String message;

    public ApiError(int errorCode, String message) {
        this.timestamp = LocalDateTime.now();
        this.errorCode = errorCode;
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
