SET SERVEROUTPUT ON;

DECLARE
    V_CURSOR      SYS_REFCURSOR;
    V_EMP_ID      EMPLOYEE.EMP_ID%TYPE;
    V_FIRST_NAME  EMPLOYEE.FIRST_NAME%TYPE;
    V_LAST_NAME   EMPLOYEE.LAST_NAME%TYPE;
    V_EMAIL       EMPLOYEE.EMAIL%TYPE;
    V_PHONE       EMPLOYEE.PHONE%TYPE;
    V_SALARY      EMPLOYEE.SALARY%TYPE;
    V_DEPARTMENT  EMPLOYEE.DEPARTMENT%TYPE;
    V_JOIN_DATE   EMPLOYEE.JOIN_DATE%TYPE;
BEGIN
    EMPLOYEE_PKG.GET_EMPLOYEE_BY_ID(
            P_EMP_ID => 1,
            P_CURSOR => V_CURSOR
    );

    FETCH V_CURSOR INTO
        V_EMP_ID,
        V_FIRST_NAME,
        V_LAST_NAME,
        V_EMAIL,
        V_PHONE,
        V_SALARY,
        V_DEPARTMENT,
        V_JOIN_DATE;

    IF V_CURSOR%FOUND THEN
        DBMS_OUTPUT.PUT_LINE(
                V_EMP_ID || ' | ' ||
                V_FIRST_NAME || ' | ' ||
                V_LAST_NAME || ' | ' ||
                V_EMAIL || ' | ' ||
                V_PHONE || ' | ' ||
                V_SALARY || ' | ' ||
                V_DEPARTMENT || ' | ' ||
                TO_CHAR(V_JOIN_DATE, 'YYYY-MM-DD')
        );
    ELSE
        DBMS_OUTPUT.PUT_LINE('NO EMPLOYEE FOUND.');
    END IF;

    CLOSE V_CURSOR;
END;
/