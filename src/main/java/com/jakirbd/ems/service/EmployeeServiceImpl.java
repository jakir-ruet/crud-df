package com.jakirbd.ems.service;

import com.jakirbd.ems.employee.dto.EmployeeRequest;
import com.jakirbd.ems.employee.dto.EmployeeResponse;
import com.jakirbd.ems.employee.entity.Employee;
import com.jakirbd.ems.employee.mapper.EmployeeMapper;
import com.jakirbd.ems.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public Long createEmployee(EmployeeRequest request){
        Employee employee = employeeMapper.toEntity(request);
        return employeeRepository.save(employee);
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id){
        Employee employee = employeeRepository.findById(id);
        if (employee == null) {
            throw new RuntimeException("Employee not found with id: " + id);
        }
        return employeeMapper.toResponse(employee);
    }

    @Override
    public List<EmployeeResponse> getAllEmployees() {

        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEmployee(Long id, EmployeeRequest request) {

        Employee existing = employeeRepository.findById(id);

        if (existing == null) {
            throw new RuntimeException("Employee not found with id: " + id);
        }

        Employee updated = employeeMapper.toEntity(request);
        updated.setEmployeeId(id);

        employeeRepository.update(updated);
    }

    @Override
    public void deleteEmployee(Long id) {

        employeeRepository.delete(id);
    }
}
