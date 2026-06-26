package com.jakirbd.employee_management_system.employee.mapper;

import com.jakirbd.employee_management_system.employee.dto.EmployeeRequest;
import com.jakirbd.employee_management_system.employee.dto.EmployeeResponse;
import com.jakirbd.employee_management_system.employee.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    public Employee toEntity(EmployeeRequest request){
        return Employee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .salary(request.getSalary())
                .department(request.getDepartment())
                .build();
    }

    public EmployeeResponse toResponse(Employee employee){
        return EmployeeResponse.builder()
                .employeeId(employee.getEmployeeId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .phone(employee.getPhone())
                .salary(employee.getSalary())
                .department(employee.getDepartment())
                .build();
    }
}
