SET SERVEROUTPUT ON;

DECLARE
    V_EMP_ID EMPLOYEE.EMP_ID%TYPE;
BEGIN
    EMPLOYEE_PKG.ADD_EMPLOYEE(
            P_FIRST_NAME => 'Sagor',
            P_LAST_NAME  => 'Chowdhury',
            P_EMAIL      => 'sagor@gmail.com',
            P_PHONE      => '01788967803',
            P_SALARY     => 65000,
            P_DEPARTMENT => 'Account',
            P_EMP_ID     => V_EMP_ID
    );

    DBMS_OUTPUT.PUT_LINE('NEW EMPLOYEE ID: ' || V_EMP_ID);
END;
/

SELECT * FROM EMPLOYEE;

UPDATE EMPLOYEE
SET EMAIL = 'karim.uddin@gmail.com'
WHERE EMP_ID = 3;