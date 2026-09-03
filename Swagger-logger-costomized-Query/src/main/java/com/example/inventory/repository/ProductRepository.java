package com.example.inventory.repository;

import com.example.inventory.entity.Product;
import com.example.inventory.entity.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByCategoryIgnoreCase(String category, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    List<Product> findByStatus(ProductStatus status);

    // JPQL query
    @Query("""
            SELECT p
            FROM Product p
            WHERE p.price >= :minPrice
            AND p.quantity > 0
            ORDER BY p.price DESC
            """)
    List<Product> findAvailableProductsAbovePrice(
            @Param("minPrice") BigDecimal minPrice
    );

    // Native SQL query
    @Query(value = """
            SELECT *
            FROM product
            WHERE quantity <= :quantity
            ORDER BY quantity ASC
            """, nativeQuery = true)
    List<Product> findLowStockProducts(@Param("quantity") Integer quantity);

    // Named Query is defined on Product using @NamedQuery.
    List<Product> findProductsByStatus(@Param("status") ProductStatus status);
}
