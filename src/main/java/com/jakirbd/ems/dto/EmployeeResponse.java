package com.jakirbd.ems.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeResponse {
	private Long empId;
   private String firstName;
   private String lastName;
   private String email;
   private String phone;
   private BigDecimal salary;
   private String department;
   private LocalDate joinDate;

	public EmployeeResponse(Long empId, String firstName, String lastName, String email,
			String phone, BigDecimal salary, String department, LocalDate joinDate) {
		this.empId = empId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.salary = salary;
		this.department = department;
		this.joinDate = joinDate;

	}
	public Long getEmpId() {
        return empId;
    }

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

    public LocalDate getJoinDate() {
        return joinDate;
    }
}
