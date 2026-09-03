package com.example.inventory.controller;

import com.example.inventory.dto.ProductRequestDto;
import com.example.inventory.dto.ProductResponseDto;
import com.example.inventory.entity.ProductStatus;
import com.example.inventory.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product API", description = "Product inventory management APIs")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Create product")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product created"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    public ResponseEntity<ProductResponseDto> createProduct(
            @Valid @RequestBody ProductRequestDto requestDto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.createProduct(requestDto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductResponseDto> getProductById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping
    @Operation(summary = "Get products with pagination and sorting")
    public ResponseEntity<Page<ProductResponseDto>> getProducts(

            @Parameter(description = "Page number, starts from 0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Number of records per page")
            @RequestParam(defaultValue = "5") int size,

            @Parameter(description = "Entity field used for sorting")
            @RequestParam(defaultValue = "id") String sortBy,

            @Parameter(description = "asc or desc")
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return ResponseEntity.ok(
                productService.getProducts(
                        page, size, sortBy, direction
                )
        );
    }

    @GetMapping("/search/category")
    @Operation(summary = "Search products by category with pagination and sorting")
    public ResponseEntity<Page<ProductResponseDto>> searchByCategory(

            @RequestParam String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return ResponseEntity.ok(
                productService.searchByCategory(
                        category, page, size, sortBy, direction
                )
        );
    }

    @GetMapping("/search/name")
    @Operation(summary = "Search products by name with pagination and sorting")
    public ResponseEntity<Page<ProductResponseDto>> searchByName(

            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return ResponseEntity.ok(
                productService.searchByName(
                        name, page, size, sortBy, direction
                )
        );
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "409", description = "Optimistic locking conflict")
    })
    public ResponseEntity<ProductResponseDto> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDto requestDto
    ) {
        return ResponseEntity.ok(
                productService.updateProduct(id, requestDto)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Product deleted"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long id
    ) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Find products using Named Query")
    public ResponseEntity<List<ProductResponseDto>> findByStatus(
            @PathVariable ProductStatus status
    ) {
        return ResponseEntity.ok(productService.findByStatus(status));
    }

    @GetMapping("/price")
    @Operation(summary = "Find available products above a price using JPQL")
    public ResponseEntity<List<ProductResponseDto>> findByMinimumPrice(
            @RequestParam BigDecimal minPrice
    ) {
        return ResponseEntity.ok(
                productService.findAvailableProductsAbovePrice(minPrice)
        );
    }

    @GetMapping("/low-stock")
    @Operation(summary = "Find low-stock products using native SQL")
    public ResponseEntity<List<ProductResponseDto>> findLowStockProducts(
            @RequestParam(defaultValue = "5") Integer quantity
    ) {
        return ResponseEntity.ok(
                productService.findLowStockProducts(quantity)
        );
    }
}
