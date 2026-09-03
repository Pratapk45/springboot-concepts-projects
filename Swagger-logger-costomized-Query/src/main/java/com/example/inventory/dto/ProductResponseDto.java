package com.example.inventory.dto;

import com.example.inventory.entity.ProductStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponseDto(
        Long id,
        String name,
        String description,
        String category,
        BigDecimal price,
        Integer quantity,
        ProductStatus status,
        LocalDateTime createdDate,
        LocalDateTime updatedDate,
        String createdBy,
        String updatedBy,
        Long version
) {
}
