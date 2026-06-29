package com.jakirbd.ems.exception;

public class EmployeeNotFoundException extends RuntimeException {
	public EmployeeNotFoundException(String message) {
		super(message);
	}
}
