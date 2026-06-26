package com.jakirbd.employee_management_system.service;

import com.jakirbd.employee_management_system.employee.dto.EmployeeRequest;
import com.jakirbd.employee_management_system.employee.dto.EmployeeResponse;

import java.util.List;

public interface EmployeeService {
    Long createEmployee(EmployeeRequest request);

    EmployeeResponse getEmployeeById(Long id);

    List<EmployeeResponse> getAllEmployees();

    void updateEmployee(Long id, EmployeeRequest request);

    void deleteEmployee(Long id);
}
