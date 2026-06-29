package com.jakirbd.ems.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EmployeeUpdateRequest {
	@NotBlank
    @Size(max = 100)
    private String firstName;

    @Size(max = 100)
    private String lastName;

    @NotBlank
    @Email
    @Size(max = 100)
    private String email;

    @NotBlank
    @Size(max = 20)
    private String phone;

    private BigDecimal salary;

    @Size(max = 20)
    private String department;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
