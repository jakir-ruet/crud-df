package com.jakirbd.ems.service;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jakirbd.ems.dto.EmployeeCreateRequest;
import com.jakirbd.ems.dto.EmployeeResponse;
import com.jakirbd.ems.dto.EmployeeUpdateRequest;
import com.jakirbd.ems.exception.EmployeeNotFoundException;
import com.jakirbd.ems.model.Employee;
import com.jakirbd.ems.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public Long createEmployee(EmployeeCreateRequest request) {
        Employee employee = new Employee();

        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setSalary(request.getSalary());
        employee.setDepartment(request.getDepartment());

        return employeeRepository.create(employee);
    }

    public EmployeeResponse getEmployeeById(Long empId) {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new EmployeeNotFoundException("EMPLOYEE NOT FOUND."));

        return toResponse(employee);
    }

    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public void updateEmployee(Long empId, EmployeeUpdateRequest request) {
        Employee employee = new Employee();

        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setSalary(request.getSalary());
        employee.setDepartment(request.getDepartment());

        employeeRepository.update(empId, employee);
    }

    @Transactional
    public void deleteEmployee(Long empId) {
        employeeRepository.delete(empId);
    }

    private EmployeeResponse toResponse(Employee employee) {
        return new EmployeeResponse(
                employee.getEmpId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getSalary(),
                employee.getDepartment(),
                employee.getJoinDate()
        );
    }
}
