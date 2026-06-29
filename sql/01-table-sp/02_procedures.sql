-------------------------------------------------------
-- CREATE PACKAGE NAME EMPLOYEE_PKG
-------------------------------------------------------
CREATE OR REPLACE PACKAGE EMPLOYEE_PKG AS
    PROCEDURE ADD_EMPLOYEE (
        P_FIRST_NAME  IN EMPLOYEE.FIRST_NAME%TYPE,
        P_LAST_NAME   IN EMPLOYEE.LAST_NAME%TYPE,
        P_EMAIL       IN EMPLOYEE.EMAIL%TYPE,
        P_PHONE       IN EMPLOYEE.PHONE%TYPE,
        P_SALARY      IN EMPLOYEE.SALARY%TYPE,
        P_DEPARTMENT  IN EMPLOYEE.DEPARTMENT%TYPE,
        P_EMP_ID      OUT EMPLOYEE.EMP_ID%TYPE
    );

    PROCEDURE GET_EMPLOYEE_BY_ID (
        P_EMP_ID IN EMPLOYEE.EMP_ID%TYPE,
        P_CURSOR OUT SYS_REFCURSOR
    );

    PROCEDURE GET_ALL_EMPLOYEES (
        P_CURSOR OUT SYS_REFCURSOR
    );

    PROCEDURE UPDATE_EMPLOYEE (
        P_EMP_ID      IN EMPLOYEE.EMP_ID%TYPE,
        P_FIRST_NAME  IN EMPLOYEE.FIRST_NAME%TYPE,
        P_LAST_NAME   IN EMPLOYEE.LAST_NAME%TYPE,
        P_EMAIL       IN EMPLOYEE.EMAIL%TYPE,
        P_PHONE       IN EMPLOYEE.PHONE%TYPE,
        P_SALARY      IN EMPLOYEE.SALARY%TYPE,
        P_DEPARTMENT  IN EMPLOYEE.DEPARTMENT%TYPE
    );

    PROCEDURE DELETE_EMPLOYEE (
        P_EMP_ID IN EMPLOYEE.EMP_ID%TYPE
    );
END EMPLOYEE_PKG;
/

-------------------------------------------------------
-- CREATE BODY NAME EMPLOYEE_PKG
-------------------------------------------------------
CREATE OR REPLACE PACKAGE BODY EMPLOYEE_PKG AS

    PROCEDURE ADD_EMPLOYEE (
        P_FIRST_NAME  IN EMPLOYEE.FIRST_NAME%TYPE,
        P_LAST_NAME   IN EMPLOYEE.LAST_NAME%TYPE,
        P_EMAIL       IN EMPLOYEE.EMAIL%TYPE,
        P_PHONE       IN EMPLOYEE.PHONE%TYPE,
        P_SALARY      IN EMPLOYEE.SALARY%TYPE,
        P_DEPARTMENT  IN EMPLOYEE.DEPARTMENT%TYPE,
        P_EMP_ID      OUT EMPLOYEE.EMP_ID%TYPE
    ) IS
    BEGIN
        INSERT INTO EMPLOYEE (
            FIRST_NAME,
            LAST_NAME,
            EMAIL,
            PHONE,
            SALARY,
            DEPARTMENT
        )
        VALUES (
               P_FIRST_NAME,
               P_LAST_NAME,
               P_EMAIL,
               P_PHONE,
               P_SALARY,
               P_DEPARTMENT
               )
        RETURNING EMP_ID INTO P_EMP_ID;

    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            RAISE_APPLICATION_ERROR(
                    -20001,
                    'EMPLOYEE EMAIL OR PHONE ALREADY EXISTS.'
            );

        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(
                    -20099,
                    'ERROR CREATING EMPLOYEE: ' || SQLERRM
            );
    END ADD_EMPLOYEE;


    PROCEDURE GET_EMPLOYEE_BY_ID (
        P_EMP_ID IN EMPLOYEE.EMP_ID%TYPE,
        P_CURSOR OUT SYS_REFCURSOR
    ) IS
    BEGIN
        OPEN P_CURSOR FOR
            SELECT
                EMP_ID,
                FIRST_NAME,
                LAST_NAME,
                EMAIL,
                PHONE,
                SALARY,
                DEPARTMENT,
                JOIN_DATE
            FROM EMPLOYEE
            WHERE EMP_ID = P_EMP_ID;
    END GET_EMPLOYEE_BY_ID;


    PROCEDURE GET_ALL_EMPLOYEES (
        P_CURSOR OUT SYS_REFCURSOR
    ) IS
    BEGIN
        OPEN P_CURSOR FOR
            SELECT
                EMP_ID,
                FIRST_NAME,
                LAST_NAME,
                EMAIL,
                PHONE,
                SALARY,
                DEPARTMENT,
                JOIN_DATE
            FROM EMPLOYEE
            ORDER BY EMP_ID;
    END GET_ALL_EMPLOYEES;


    PROCEDURE UPDATE_EMPLOYEE (
        P_EMP_ID      IN EMPLOYEE.EMP_ID%TYPE,
        P_FIRST_NAME  IN EMPLOYEE.FIRST_NAME%TYPE,
        P_LAST_NAME   IN EMPLOYEE.LAST_NAME%TYPE,
        P_EMAIL       IN EMPLOYEE.EMAIL%TYPE,
        P_PHONE       IN EMPLOYEE.PHONE%TYPE,
        P_SALARY      IN EMPLOYEE.SALARY%TYPE,
        P_DEPARTMENT  IN EMPLOYEE.DEPARTMENT%TYPE
    ) IS
    BEGIN
        UPDATE EMPLOYEE
        SET FIRST_NAME = P_FIRST_NAME,
            LAST_NAME  = P_LAST_NAME,
            EMAIL      = P_EMAIL,
            PHONE      = P_PHONE,
            SALARY     = P_SALARY,
            DEPARTMENT = P_DEPARTMENT
        WHERE EMP_ID = P_EMP_ID;

        IF SQL%ROWCOUNT = 0 THEN
            RAISE_APPLICATION_ERROR(
                    -20002,
                    'EMPLOYEE NOT FOUND.'
            );
        END IF;

    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            RAISE_APPLICATION_ERROR(
                    -20001,
                    'EMPLOYEE EMAIL OR PHONE ALREADY EXISTS.'
            );

        WHEN OTHERS THEN
            IF SQLCODE BETWEEN -20999 AND -20000 THEN
                RAISE;
            ELSE
                RAISE_APPLICATION_ERROR(
                        -20099,
                        'ERROR UPDATING EMPLOYEE: ' || SQLERRM
                );
            END IF;
    END UPDATE_EMPLOYEE;


    PROCEDURE DELETE_EMPLOYEE (
        P_EMP_ID IN EMPLOYEE.EMP_ID%TYPE
    ) IS
    BEGIN
        DELETE FROM EMPLOYEE
        WHERE EMP_ID = P_EMP_ID;

        IF SQL%ROWCOUNT = 0 THEN
            RAISE_APPLICATION_ERROR(
                    -20002,
                    'EMPLOYEE NOT FOUND.'
            );
        END IF;

    EXCEPTION
        WHEN OTHERS THEN
            IF SQLCODE BETWEEN -20999 AND -20000 THEN
                RAISE;
            ELSE
                RAISE_APPLICATION_ERROR(
                        -20099,
                        'ERROR DELETING EMPLOYEE: ' || SQLERRM
                );
            END IF;
    END DELETE_EMPLOYEE;

END EMPLOYEE_PKG;
/