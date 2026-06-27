package com.jakirbd.ems.service;

import com.jakirbd.ems.employee.dto.EmployeeRequest;
import com.jakirbd.ems.employee.dto.EmployeeResponse;

import java.util.List;

public interface EmployeeService {
    Long createEmployee(EmployeeRequest request);

    EmployeeResponse getEmployeeById(Long id);

    List<EmployeeResponse> getAllEmployees();

    void updateEmployee(Long id, EmployeeRequest request);

    void deleteEmployee(Long id);
}
