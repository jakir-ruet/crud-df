package com.jakirbd.employee_management_system.controller;

import com.jakirbd.employee_management_system.employee.dto.EmployeeRequest;
import com.jakirbd.employee_management_system.employee.dto.EmployeeResponse;
import com.jakirbd.employee_management_system.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor

public class EmployeeController {
    private final EmployeeService employeeService;

    // Create
    @PostMapping
    public ResponseEntity<Long> createEmployee(
            @RequestBody @Valid EmployeeRequest request) {

        Long id = employeeService.createEmployee(request);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    // Get By ID
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable Long id) {
        return  ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    // Get All
    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequest request) {
        employeeService.updateEmployee(id, request);
        return ResponseEntity.noContent().build();
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
