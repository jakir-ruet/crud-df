package com.jakirbd.ems.employee.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Employee {
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private BigDecimal salary;
    private String department;
    private LocalDate joinDate;
}

// @Entity
// @Table(name="EMPLOYEE")
// These are not use because we are using here JDBC
// These are applicable for JPA/Hibernate