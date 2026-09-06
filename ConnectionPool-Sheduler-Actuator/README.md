# Employee Leave Management System

Spring Boot learning project for:
- MySQL + JPA/Hibernate
- HikariCP connection pool
- Spring Scheduler
- Spring Boot Actuator

## Requirements
- Java 17+
- Maven
- MySQL

## Database
Create the database:

```sql
CREATE DATABASE employee_leave_db;
```

Update MySQL username/password in:
`src/main/resources/application.properties`

## Run
```bash
mvn spring-boot:run
```

## Employee APIs
POST   /employees
GET    /employees
GET    /employees/{id}
DELETE /employees/{id}

## Leave APIs
POST   /leaves
GET    /leaves
GET    /leaves/employee/{employeeId}
PUT    /leaves/{id}/status?status=APPROVED
DELETE /leaves/{id}

## Actuator
GET /actuator/health
GET /actuator/info
GET /actuator/metrics
GET /actuator/metrics/hikaricp.connections.active
GET /actuator/metrics/hikaricp.connections.idle
GET /actuator/metrics/hikaricp.connections.max

## Scheduler
ConnectionPoolScheduler prints HikariCP statistics every 10 seconds.
