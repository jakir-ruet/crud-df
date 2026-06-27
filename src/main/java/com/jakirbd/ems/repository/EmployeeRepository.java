package com.jakirbd.ems.repository;

import com.jakirbd.ems.employee.entity.Employee;

import java.util.List;

public interface EmployeeRepository {
    Long save(Employee employee);
    Employee findById(Long employeeId);
    List<Employee> findAll();
    void update(Employee employee);
    void delete(Long employeeId);
}
