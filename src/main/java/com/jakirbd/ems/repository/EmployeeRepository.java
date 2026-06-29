package com.jakirbd.ems.repository;

import java.util.List;
import java.util.Optional;

import com.jakirbd.ems.model.Employee;

public interface EmployeeRepository {
	Long create(Employee employee);

    Optional<Employee> findById(Long empId);

    List<Employee> findAll();

    void update(Long empId, Employee employee);

    void delete(Long empId);
}
