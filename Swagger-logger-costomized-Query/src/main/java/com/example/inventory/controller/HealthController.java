package com.example.inventory.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/api/health")
    @Operation(summary = "Simple application health endpoint")
    public Map<String, String> health() {
        return Map.of(
                "application", "Product Inventory Management",
                "status", "UP"
        );
    }
}
