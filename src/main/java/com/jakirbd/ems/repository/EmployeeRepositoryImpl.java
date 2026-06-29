package com.jakirbd.ems.repository;

import com.jakirbd.ems.model.Employee;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.ConnectionCallback;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.sql.Date;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

	private final JdbcTemplate jdbcTemplate;
	 private final DataSource dataSource;

    public EmployeeRepositoryImpl(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
		  this.dataSource = dataSource;
    }

	@Override
   public Long create(Employee employee) {
    String sql = "{call EMPLOYEE_PKG.ADD_EMPLOYEE(?, ?, ?, ?, ?, ?, ?)}";

    return jdbcTemplate.execute((ConnectionCallback<Long>) connection -> {
        try (CallableStatement cs = connection.prepareCall(sql)) {

            cs.setString(1, employee.getFirstName());
            cs.setString(2, employee.getLastName());
            cs.setString(3, employee.getEmail());
            cs.setString(4, employee.getPhone());
            cs.setBigDecimal(5, employee.getSalary());
            cs.setString(6, employee.getDepartment());
            cs.registerOutParameter(7, Types.NUMERIC);

            cs.execute();

            return cs.getLong(7);
        }
    });
}

	@Override
	public Optional<Employee> findById(Long empId) {
		String sql = "{call EMPLOYEE_PKG.GET_EMPLOYEE_BY_ID(?, ?)}";

		return jdbcTemplate.execute((ConnectionCallback<Optional<Employee>>) connection -> {
			try (CallableStatement cs = connection.prepareCall(sql)) {

					cs.setLong(1, empId);
					cs.registerOutParameter(2, Types.REF_CURSOR);

					cs.execute();

					try (ResultSet rs = (ResultSet) cs.getObject(2)) {
						if (rs.next()) {
							return Optional.of(mapRow(rs));
						}
						return Optional.empty();
					}
			}
		});
	}

    @Override
public List<Employee> findAll() {
    String sql = "{call EMPLOYEE_PKG.GET_ALL_EMPLOYEES(?)}";

    return jdbcTemplate.execute((ConnectionCallback<List<Employee>>) connection -> {
        List<Employee> employees = new ArrayList<>();

        try (CallableStatement cs = connection.prepareCall(sql)) {
            cs.registerOutParameter(1, Types.REF_CURSOR);
            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {
                while (rs.next()) {
                    employees.add(mapRow(rs));
                }
            }
        }

        return employees;
    });
}

    @Override
public void update(Long empId, Employee employee) {
    String sql = "{call EMPLOYEE_PKG.UPDATE_EMPLOYEE(?, ?, ?, ?, ?, ?, ?)}";

    jdbcTemplate.execute((ConnectionCallback<Void>) connection -> {
        try (CallableStatement cs = connection.prepareCall(sql)) {
            cs.setLong(1, empId);
            cs.setString(2, employee.getFirstName());
            cs.setString(3, employee.getLastName());
            cs.setString(4, employee.getEmail());
            cs.setString(5, employee.getPhone());
            cs.setBigDecimal(6, employee.getSalary());
            cs.setString(7, employee.getDepartment());

            cs.execute();
            return null;
        }
    });
}

    @Override
public void delete(Long empId) {

    String sql = "{call EMPLOYEE_PKG.DELETE_EMPLOYEE(?)}";

    try (
        Connection connection = dataSource.getConnection();
        CallableStatement cs = connection.prepareCall(sql)
    ) {

        cs.setLong(1, empId);

        cs.execute();

    } catch (SQLException ex) {
        throw new RuntimeException(ex);
    }
}

    private Employee mapRow(ResultSet rs) throws java.sql.SQLException {
        Date joinDate = rs.getDate("JOIN_DATE");

        return new Employee(
                rs.getLong("EMP_ID"),
                rs.getString("FIRST_NAME"),
                rs.getString("LAST_NAME"),
                rs.getString("EMAIL"),
                rs.getString("PHONE"),
                rs.getBigDecimal("SALARY"),
                rs.getString("DEPARTMENT"),
                joinDate != null ? joinDate.toLocalDate() : null
        );
    }
}
