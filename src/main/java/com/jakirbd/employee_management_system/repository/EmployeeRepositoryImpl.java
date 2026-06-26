package com.jakirbd.employee_management_system.repository;

import com.jakirbd.employee_management_system.employee.entity.Employee;

import com.jakirbd.employee_management_system.employee.mapper.EmployeeRowMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor

public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final EmployeeRowMapper employeeRowMapper;

    private SimpleJdbcCall addEmployeeCall;
    private SimpleJdbcCall getEmployeeByIdCall;
    private SimpleJdbcCall getAllEmployeesCall;
    private SimpleJdbcCall updateEmployeeCall;
    private SimpleJdbcCall deleteEmployeeCall;

    @PostConstruct
    public void init() {
        addEmployeeCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("EMPLOYEE_PKG")
                .withProcedureName("ADD_EMPLOYEE")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("P_FIRST_NAME", Types.VARCHAR),
                        new SqlParameter("P_LAST_NAME", Types.VARCHAR),
                        new SqlParameter("P_EMAIL", Types.VARCHAR),
                        new SqlParameter("P_PHONE", Types.VARCHAR),
                        new SqlParameter("P_SALARY", Types.NUMERIC),
                        new SqlParameter("P_DEPARTMENT", Types.VARCHAR),
                        new SqlOutParameter("P_EMP_ID", Types.NUMERIC)
                );

        getEmployeeByIdCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("EMPLOYEE_PKG")
                .withProcedureName("GET_EMPLOYEE_BY_ID")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("P_EMP_ID", Types.NUMERIC),
                        new SqlOutParameter(
                                "P_CURSOR",
                                Types.REF_CURSOR,
                                employeeRowMapper
                        )
                );

        getAllEmployeesCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("EMPLOYEE_PKG")
                .withProcedureName("GET_ALL_EMPLOYEES")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter(
                                "P_CURSOR",
                                Types.REF_CURSOR,
                                employeeRowMapper
                        )
                );

        updateEmployeeCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("EMPLOYEE_PKG")
                .withProcedureName("UPDATE_EMPLOYEE")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("P_EMP_ID", Types.NUMERIC),
                        new SqlParameter("P_FIRST_NAME", Types.VARCHAR),
                        new SqlParameter("P_LAST_NAME", Types.VARCHAR),
                        new SqlParameter("P_EMAIL", Types.VARCHAR),
                        new SqlParameter("P_PHONE", Types.VARCHAR),
                        new SqlParameter("P_SALARY", Types.NUMERIC),
                        new SqlParameter("P_DEPARTMENT", Types.VARCHAR)
                );

        deleteEmployeeCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("EMPLOYEE_PKG")
                .withProcedureName("DELETE_EMPLOYEE")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("P_EMP_ID", Types.NUMERIC)
                );
    }

    @Override
    public Long save(Employee employee) {

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("P_FIRST_NAME", employee.getFirstName());
        parameters.addValue("P_LAST_NAME", employee.getLastName());
        parameters.addValue("P_EMAIL", employee.getEmail());
        parameters.addValue("P_PHONE", employee.getPhone());
        parameters.addValue("P_SALARY", employee.getSalary());
        parameters.addValue("P_DEPARTMENT", employee.getDepartment());

        Map<String, Object> result = addEmployeeCall.execute(parameters);

        Number employeeId = (Number) result.get("P_EMP_ID");

        return employeeId.longValue();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Employee findById(Long employeeId) {

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("P_EMP_ID", employeeId);

        Map<String, Object> result = getEmployeeByIdCall.execute(parameters);

        List<Employee> employees =
                (List<Employee>) result.get("P_CURSOR");

        if (employees == null || employees.isEmpty()) {
            return null;
        }

        return employees.getFirst();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Employee> findAll() {

        Map<String, Object> result = getAllEmployeesCall.execute();
        return (List<Employee>) result.get("P_CURSOR");
    }

    @Override
    public void update(Employee employee) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();

        parameters.addValue("P_EMP_ID", employee.getEmployeeId());
        parameters.addValue("P_FIRST_NAME", employee.getFirstName());
        parameters.addValue("P_LAST_NAME", employee.getLastName());
        parameters.addValue("P_EMAIL", employee.getEmail());
        parameters.addValue("P_PHONE", employee.getPhone());
        parameters.addValue("P_SALARY", employee.getSalary());
        parameters.addValue("P_DEPARTMENT", employee.getDepartment());

        updateEmployeeCall.execute(parameters);
    }

    @Override
    public void delete(Long employeeId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();

        parameters.addValue("P_EMP_ID", employeeId);

        deleteEmployeeCall.execute(parameters);
    }
}
