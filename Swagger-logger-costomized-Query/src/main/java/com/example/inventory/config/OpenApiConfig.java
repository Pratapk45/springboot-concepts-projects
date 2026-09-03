package com.example.inventory.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Product Inventory Management API",
                version = "1.0",
                description = "REST API demonstrating CRUD, Pagination, Sorting, MapStruct, Auditing, Versioning, JPQL, Native SQL and Named Queries",
                contact = @Contact(
                        name = "Inventory API Team"
                )
        )
)
public class OpenApiConfig {
}
