package com.jakirbd.ems.employee.mapper;

import com.jakirbd.ems.employee.entity.Employee;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class EmployeeRowMapper implements RowMapper<Employee> {

    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {

        Employee employee = new Employee();

        employee.setEmployeeId(rs.getLong("EMP_ID"));
        employee.setFirstName(rs.getString("FIRST_NAME"));
        employee.setLastName(rs.getString("LAST_NAME"));
        employee.setEmail(rs.getString("EMAIL"));
        employee.setPhone(rs.getString("PHONE"));
        employee.setSalary(rs.getBigDecimal("SALARY"));
        employee.setDepartment(rs.getString("DEPARTMENT"));

        if (rs.getDate("JOIN_DATE") != null) {
            employee.setJoinDate(rs.getDate("JOIN_DATE").toLocalDate());
        }

        return employee;
    }
}
