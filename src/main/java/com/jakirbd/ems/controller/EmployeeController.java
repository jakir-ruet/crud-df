package com.jakirbd.ems.controller;

import com.jakirbd.ems.dto.EmployeeCreateRequest;
import com.jakirbd.ems.dto.EmployeeResponse;
import com.jakirbd.ems.dto.EmployeeUpdateRequest;
import com.jakirbd.ems.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/employees")

public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> createEmployee(@Valid @RequestBody EmployeeCreateRequest request) {
        Long empId = employeeService.createEmployee(request);

        return Map.of(
                "message", "EMPLOYEE CREATED SUCCESSFULLY.",
                "empId", empId
        );
    }

    @GetMapping("/{empId}")
    public EmployeeResponse getEmployeeById(@PathVariable Long empId) {
        return employeeService.getEmployeeById(empId);
    }

    @GetMapping
    public List<EmployeeResponse> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PutMapping("/{empId}")
    public Map<String, Object> updateEmployee(
            @PathVariable Long empId,
            @Valid @RequestBody EmployeeUpdateRequest request
    ) {
        employeeService.updateEmployee(empId, request);

        return Map.of(
                "message", "EMPLOYEE UPDATED SUCCESSFULLY.",
                "empId", empId
        );
    }

    @DeleteMapping("/{empId}")
    public Map<String, Object> deleteEmployee(@PathVariable Long empId) {
        employeeService.deleteEmployee(empId);

        return Map.of(
                "message", "EMPLOYEE DELETED SUCCESSFULLY.",
                "empId", empId
        );
    }
}
