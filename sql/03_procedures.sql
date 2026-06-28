CREATE OR REPLACE PACKAGE employee_pkg AS
    PROCEDURE add_employee (
        p_first_name IN employee.first_name%TYPE,
        p_last_name  IN employee.last_name%TYPE,
        p_email      IN employee.email%TYPE,
        p_phone      IN employee.phone%TYPE,
        p_salary     IN employee.salary%TYPE,
        p_department IN employee.department%TYPE,
        p_emp_id     OUT employee.emp_id%TYPE
    );

    PROCEDURE get_employee_by_id (
        p_emp_id IN employee.emp_id%TYPE,
        p_cursor OUT SYS_REFCURSOR
    );

    PROCEDURE get_all_employees (
        p_cursor OUT SYS_REFCURSOR
    );

    PROCEDURE update_employee (
        p_emp_id     IN employee.emp_id%TYPE,
        p_first_name IN employee.first_name%TYPE,
        p_last_name  IN employee.last_name%TYPE,
        p_email      IN employee.email%TYPE,
        p_phone      IN employee.phone%TYPE,
        p_salary     IN employee.salary%TYPE,
        p_department IN employee.department%TYPE
    );

    PROCEDURE delete_employee (
        p_emp_id IN employee.emp_id%TYPE
    );
END employee_pkg;
/

CREATE OR REPLACE PACKAGE BODY employee_pkg AS

    ----------------------------------------------------------------------------
    -- Create Employee
    ----------------------------------------------------------------------------
    PROCEDURE add_employee (
        p_first_name IN employee.first_name%TYPE,
        p_last_name  IN employee.last_name%TYPE,
        p_email      IN employee.email%TYPE,
        p_phone      IN employee.phone%TYPE,
        p_salary     IN employee.salary%TYPE,
        p_department IN employee.department%TYPE,
        p_emp_id     OUT employee.emp_id%TYPE
    ) IS
BEGIN
    SELECT employee_seq.NEXTVAL
    INTO p_emp_id
    FROM dual;

INSERT INTO employee (
    emp_id,
    first_name,
    last_name,
    email,
    phone,
    salary,
    department,
    join_date
)
VALUES (
       p_emp_id,
       p_first_name,
       p_last_name,
       p_email,
       p_phone,
       p_salary,
       p_department,
       SYSDATE
   );

EXCEPTION
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(
                -20001,
                'Error creating employee: ' || SQLERRM
            );
END add_employee;

    ----------------------------------------------------------------------------
    -- Get Employee by ID
    ----------------------------------------------------------------------------
    PROCEDURE get_employee_by_id (
        p_emp_id IN employee.emp_id%TYPE,
        p_cursor OUT SYS_REFCURSOR
    ) IS
BEGIN
    OPEN p_cursor FOR
    SELECT
        emp_id,
        first_name,
        last_name,
        email,
        phone,
        salary,
        department,
        join_date
FROM employee
    WHERE emp_id = p_emp_id;
    END get_employee_by_id;

        ----------------------------------------------------------------------------
        -- Get All Employees
        ----------------------------------------------------------------------------
        PROCEDURE get_all_employees (
            p_cursor OUT SYS_REFCURSOR
        ) IS
BEGIN
    OPEN p_cursor FOR
    SELECT
        emp_id,
        first_name,
        last_name,
        email,
        phone,
        salary,
        department,
        join_date
    FROM employee
    ORDER BY emp_id;
END get_all_employees;

    ----------------------------------------------------------------------------
    -- Update Employee
    ----------------------------------------------------------------------------
    PROCEDURE update_employee (
        p_emp_id     IN employee.emp_id%TYPE,
        p_first_name IN employee.first_name%TYPE,
        p_last_name  IN employee.last_name%TYPE,
        p_email      IN employee.email%TYPE,
        p_phone      IN employee.phone%TYPE,
        p_salary     IN employee.salary%TYPE,
        p_department IN employee.department%TYPE
    ) IS
BEGIN
UPDATE employee
SET first_name = p_first_name,
    last_name  = p_last_name,
    email      = p_email,
    phone      = p_phone,
    salary     = p_salary,
    department = p_department
WHERE emp_id = p_emp_id;

IF SQL%ROWCOUNT = 0 THEN
            RAISE_APPLICATION_ERROR(
                -20002,
                'Employee not found.'
            );
END IF;

EXCEPTION
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(
                -20003,
                'Error updating employee: ' || SQLERRM
            );
END update_employee;

    ----------------------------------------------------------------------------
    -- Delete Employee
    ----------------------------------------------------------------------------
    PROCEDURE delete_employee (
        p_emp_id IN employee.emp_id%TYPE
    ) IS
BEGIN
DELETE FROM employee
WHERE emp_id = p_emp_id;

IF SQL%ROWCOUNT = 0 THEN
            RAISE_APPLICATION_ERROR(
                -20004,
                'Employee not found.'
            );
END IF;

EXCEPTION
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(
                -20005,
                'Error deleting employee: ' || SQLERRM
            );
END delete_employee;

END employee_pkg;
/

