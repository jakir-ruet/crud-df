### Technology Stack

```bash
| Component                    | What it is                     | Role in System                     | How it is used (Enterprise context)                     |
| ---------------------------- | ------------------------------ | ---------------------------------- | ------------------------------------------------------- |
| **Java 21**                  | Latest LTS Java version        | Core application language          | Builds service layer, business logic, REST APIs         |
| **Oracle Database 23ai**     | Enterprise relational database | Data storage + AI-enabled features | Stores tables, procedures, packages, supports SQL/PLSQL |
| **JDBC**                     | Java Database Connectivity API | Database communication layer       | Executes SQL, calls stored procedures from Java         |
| **Maven**                    | Build & dependency tool        | Project management                 | Manages dependencies (JDBC, HikariCP, SLF4J, etc.)      |
| **Layered Architecture**     | System design pattern          | Separation of concerns             | Splits app into Controller → Service → Repository       |
| **Repository Pattern**       | Data access abstraction        | Database interaction layer         | Encapsulates SQL/JDBC logic away from business code     |
| **Service Layer**            | Business logic layer           | Core processing unit               | Implements business rules, calls repositories           |
| **DTO Pattern**              | Data Transfer Object           | Data carrier                       | Transfers clean data between layers (no DB logic)       |
| **Stored Procedures**        | Precompiled DB logic           | Database-side business logic       | Called from JDBC for performance & security             |
| **Oracle SQL**               | Query language                 | Data manipulation                  | SELECT/INSERT/UPDATE/DELETE operations                  |
| **HikariCP Connection Pool** | High-performance JDBC pool     | Connection management              | Reuses DB connections efficiently (production standard) |
| **SLF4J + Logback**          | Logging framework              | Monitoring & debugging             | Logs SQL calls, errors, system events                   |
| **Lombok (optional)**        | Code generator library         | Reduces boilerplate                | Auto-generates getters, setters, constructors           |
| **JUnit 5**                  | Testing framework              | Unit + integration testing         | Tests service/repository layers                         |
```

### Architecture

```bash
Employee Management System
        │
        ▼
Console Application
        │
        ▼
EmployeeController
        │
        ▼
EmployeeService
        │
        ▼
EmployeeRepository (Interface)
        │
        ▼
EmployeeRepositoryImpl (JDBC)
        │
CallableStatement
        │
        ▼
Oracle Stored Procedures
        │
        ▼
Oracle Database 23ai
```

### Complete Package Structure

```bash
employee-management-system
│
├── pom.xml
│
├── README.md
│
├── sql
│     ├── 01_create_tables.sql
│     ├── 02_sequences.sql
│     ├── 03_procedures.sql
│     ├── 04_sample_data.sql
│     └── 05_drop.sql
│
├── logs
│
├── src
│
│── main
│   │
│   ├── java
│   │
│   └── com
│       └── jakirbd
│           └── ems
│               │
│               ├── config
│               │      DatabaseConfig.java
│               │      HikariConnection.java
│               │
│               ├── constants
│               │      AppConstants.java
│               │
│               ├── controller
│               │      EmployeeController.java
│               │
│               ├── dto
│               │      EmployeeDTO.java
│               │
│               ├── entity
│               │      Employee.java
│               │
│               ├── exception
│               │      DatabaseException.java
│               │      ValidationException.java
│               │      EmployeeNotFoundException.java
│               │
│               ├── mapper
│               │      EmployeeMapper.java
│               │
│               ├── repository
│               │      EmployeeRepository.java
│               │      EmployeeRepositoryImpl.java
│               │
│               ├── service
│               │      EmployeeService.java
│               │      EmployeeServiceImpl.java
│               │
│               ├── util
│               │      OracleProcedureUtil.java
│               │      DateUtil.java
│               │
│               ├── validator
│               │      EmployeeValidator.java
│               │
│               └── App.java
│
└── resources
      application.properties
      logback.xml
```

### This follows a layered architecture:

```bash
Controller
      │
      ▼
Service
      │
      ▼
Repository (DAO)
      │
      ▼
Oracle Stored Procedure
      │
      ▼
Oracle Database
```

### What each package does

```bash
| Package      | Responsibility                                             |
| ------------ | ---------------------------------------------------------- |
| `config`     | Database connection, configuration loading                 |
| `entity`     | Java POJOs that represent database tables                  |
| `repository` | Database access (JDBC, stored procedures, SQL)             |
| `service`    | Business logic and validation                              |
| `controller` | Handles user requests (Console, REST, or UI)               |
| `exception`  | Custom exceptions                                          |
| `util`       | Utility classes (constants, procedure helpers, converters) |
| `resources`  | Configuration files                                        |
```

### Enterprise Dependencies & Tools Table

```bash
| Dependency / Tool       | Maven Artifact                        | Purpose                                 | Why used in this project                      |
| ----------------------- | ------------------------------------- | --------------------------------------- | --------------------------------------------- |
| Spring Web              | `spring-boot-starter-web`             | Build REST APIs (Controller layer)      | To expose endpoints for Postman testing       |
| Spring JDBC             | `spring-boot-starter-jdbc`            | JDBC support + JdbcTemplate             | To call Oracle stored procedures efficiently  |
| Oracle JDBC Driver      | `ojdbc11`                             | Connect Java ↔ Oracle DB 23ai           | Required for database communication           |
| Validation              | `spring-boot-starter-validation`      | Bean validation (`@NotNull`, `@Email`)  | Input validation at API level                 |
| Lombok                  | `lombok`                              | Reduce boilerplate code                 | Removes getters/setters/constructors manually |
| Spring Boot Test        | `spring-boot-starter-test`            | Unit & integration testing              | JUnit + Mockito support                       |
| DevTools                | `spring-boot-devtools`                | Auto restart during development         | Faster development cycle                      |
| OpenAPI / Swagger       | `springdoc-openapi-starter-webmvc-ui` | API documentation UI                    | Test APIs visually instead of only Postman    |
| Configuration Processor | `spring-boot-configuration-processor` | Metadata for `@ConfigurationProperties` | Cleaner config handling in enterprise apps    |
| Logback (built-in)      | Included in Boot                      | Logging framework                       | Default production logging system             |
| SLF4J (built-in)        | Included in Boot                      | Logging API                             | Standard logging abstraction                  |
```

### Core Technology Stack View

```bash
| Layer             | Technology Used                    |
| ----------------- | ---------------------------------- |
| REST Layer        | Spring Web                         |
| Business Layer    | Spring Core + Java                 |
| Data Access Layer | Spring JDBC                        |
| Database          | Oracle 23ai                        |
| Procedure Call    | CallableStatement / SimpleJdbcCall |
| Validation        | Bean Validation                    |
| Logging           | SLF4J + Logback                    |
| API Testing       | Postman / Swagger UI               |
| Build Tool        | Maven                              |
| Language          | Java 21                            |
```

### We'll create - Database Objects

- EMPLOYEE
- DEPARTMENT
- AUDIT_LOG

#### Oracle objects

```bash
| Component            | What it is                                                                  | Purpose / Use                                                                         |
| -------------------- | --------------------------------------------------------------------------- | ------------------------------------------------------------------------------------- |
| **Sequence**         | A database object that generates numeric values automatically               | Used to create unique numbers (e.g., primary keys) in a controlled order              |
| **Trigger**          | A special procedure that runs automatically on table events                 | Executes automatically on INSERT, UPDATE, or DELETE to enforce rules or audit changes |
| **Package**          | A collection of related procedures, functions, variables (mainly in Oracle) | Organizes PL/SQL code into modular, reusable units                                    |
| **Stored Procedure** | A precompiled set of SQL statements stored in the database                  | Used to perform repeated business logic or complex operations                         |
| **Function**         | A database routine that returns a single value                              | Used in queries or calculations (must return a value)                                 |
| **Cursor**           | A database pointer to a result set                                          | Used to process query results row-by-row                                              |
| **View**             | A virtual table based on a SELECT query                                     | Simplifies complex queries and provides controlled data access                        |
```

### Stored Procedures

- ADD_EMPLOYEE
- UPDATE_EMPLOYEE
- DELETE_EMPLOYEE
- GET_EMPLOYEE
- GET_ALL_EMPLOYEES
- SEARCH_BY_DEPARTMENT
- INCREASE_SALARY
- EMPLOYEE_COUNT

### Oracle Package - Instead of individual procedures, we'll also create

```bash
CREATE PACKAGE EMPLOYEE_PACKAGE
```

#### Inside packages like

- ADD_EMPLOYEE()
- UPDATE_EMPLOYEE()
- DELETE_EMPLOYEE()
- GET_EMPLOYEE()
- GET_ALL_EMPLOYEES()

> which is how many enterprise Oracle systems are organized.

### JDBC Features - We'll demonstrate

- Connection Pool (HikariCP)
- CallableStatement
- PreparedStatement
- ResultSet
- Transactions
- Commit
- Rollback
- Batch Processing
- REF CURSOR
- OUT Parameters

### Enterprise Coding Standards - We'll follow:

- SOLID principles
- Repository Pattern
- Service Pattern
- Dependency Injection (constructor injection)
- Exception Handling
- Logging
- JavaDocs
- Validation
- Generic Utilities
- Clean Code

### DatabaseConfig - Why this setup is enterprise-grade

```bash
| Component           | Why it is used                        |
| ------------------- | ------------------------------------- |
| HikariCP            | Fast production-ready connection pool |
| JdbcTemplate        | Clean JDBC abstraction                |
| DataSource          | Central DB connection manager         |
| External properties | No hardcoded credentials              |
| Config class        | Clean separation of concerns          |
```

#### What is happening internally

```bash
Spring Boot Startup
        ↓
application.properties loaded
        ↓
HikariCP DataSource created
        ↓
JdbcTemplate injected
        ↓
Repository uses JdbcTemplate
        ↓
Calls Oracle Stored Procedures
```