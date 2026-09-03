package com.example.inventory.service;

import com.example.inventory.dto.ProductRequestDto;
import com.example.inventory.dto.ProductResponseDto;
import com.example.inventory.entity.ProductStatus;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    ProductResponseDto createProduct(ProductRequestDto requestDto);

    ProductResponseDto getProductById(Long id);

    Page<ProductResponseDto> getProducts(
            int page,
            int size,
            String sortBy,
            String direction
    );

    Page<ProductResponseDto> searchByCategory(
            String category,
            int page,
            int size,
            String sortBy,
            String direction
    );

    Page<ProductResponseDto> searchByName(
            String name,
            int page,
            int size,
            String sortBy,
            String direction
    );

    ProductResponseDto updateProduct(Long id, ProductRequestDto requestDto);

    void deleteProduct(Long id);

    List<ProductResponseDto> findByStatus(ProductStatus status);

    List<ProductResponseDto> findAvailableProductsAbovePrice(BigDecimal minPrice);

    List<ProductResponseDto> findLowStockProducts(Integer quantity);
}
