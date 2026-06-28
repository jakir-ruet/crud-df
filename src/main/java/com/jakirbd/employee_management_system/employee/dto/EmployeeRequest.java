package com.jakirbd.employee_management_system.employee.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data

public class EmployeeRequest {
    @NotBlank
    private String firstName;

    private String lastName;

    @Email
    @NotBlank
    private String email;

    private String phone;

    @NotNull
    @Positive
    private BigDecimal salary;

    @NotBlank
    private String department;

    @NotBlank
    private LocalDate javaData;
}

// Why two DTOs?
// Request DTO
// Contains only what the client sends.

//{
//    "firstName": "Jakir",
//    "lastName": "Hasan",
//    "email": "jakir@gmail.com",
//    "phone": "01700000000",
//    "salary": 50000,
//    "department": "IT"
//}