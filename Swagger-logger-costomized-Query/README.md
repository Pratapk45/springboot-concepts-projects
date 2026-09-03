# Product Inventory Management System

Spring Boot project created to understand:

- CRUD
- DTO
- MapStruct
- Pagination
- Sorting
- JPA Auditing
- `@Version`
- Optimistic Locking
- Swagger / OpenAPI
- SLF4J Logging
- JPQL
- Native SQL
- Named Query
- Validation
- Global Exception Handling

## Technology

- Java 17
- Spring Boot 4.0.7
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- MapStruct
- Lombok
- Swagger/OpenAPI

## Database

MySQL must be running.

The configured database is:

`inventory_db`

Change username/password in `application.properties`.

## Run

```bash
mvn clean install
mvn spring-boot:run
```

## Swagger

Open:

`http://localhost:8080/swagger-ui.html`

## Important APIs

POST `/api/products`

GET `/api/products/1`

GET `/api/products?page=0&size=5&sortBy=price&direction=desc`

GET `/api/products/search/category?category=MOBILE&page=0&size=5&sortBy=price&direction=asc`

GET `/api/products/search/name?name=iphone&page=0&size=5`

PUT `/api/products/1`

DELETE `/api/products/1`

GET `/api/products/status/ACTIVE`

GET `/api/products/price?minPrice=50000`

GET `/api/products/low-stock?quantity=5`

## Versioning test

`@Version` is in `BaseEntity`.

When a product is first inserted:

`version = 0`

After an update:

`version = 1`

The version is managed by JPA/Hibernate. Do not manually increment it.

For a real concurrent test, retrieve the same product in two transactions/sessions, then update it concurrently. The second stale update should fail with an optimistic locking conflict.

## Auditing

The following fields are automatically populated:

- createdDate
- updatedDate
- createdBy
- updatedBy

`AuditorAware` currently returns `system` because Spring Security is intentionally not part of this project.

## MapStruct

`ProductMapper` is an interface.

Maven annotation processing generates the implementation during compilation.

The generated class is normally under:

`target/generated-sources/annotations`

## Queries

### Derived Query

`findByCategoryIgnoreCase`

### JPQL

`findAvailableProductsAbovePrice`

### Native SQL

`findLowStockProducts`

### Named Query

`Product.findProductsByStatus`

## Notes

For production systems, validate allowed sorting fields rather than accepting arbitrary entity property names directly from the request.
