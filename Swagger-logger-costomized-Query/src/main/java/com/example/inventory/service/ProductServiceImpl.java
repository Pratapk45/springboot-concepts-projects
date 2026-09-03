package com.example.inventory.service;

import com.example.inventory.dto.ProductRequestDto;
import com.example.inventory.dto.ProductResponseDto;
import com.example.inventory.entity.Product;
import com.example.inventory.entity.ProductStatus;
import com.example.inventory.exception.ProductNotFoundException;
import com.example.inventory.mapper.ProductMapper;
import com.example.inventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

	private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

	private final ProductRepository productRepository;
	private final ProductMapper productMapper;

	@Override
	public ProductResponseDto createProduct(ProductRequestDto requestDto) {
		log.info("Creating product: {}", requestDto.name());

		Product product = productMapper.toEntity(requestDto);
		Product savedProduct = productRepository.save(product);

		log.info("Product created successfully with id: {}", savedProduct.getId());

		return productMapper.toResponseDto(savedProduct);
	}

	@Override
	@Transactional(readOnly = true)
	public ProductResponseDto getProductById(Long id) {
		log.debug("Fetching product with id: {}", id);

		Product product = findProduct(id);

		return productMapper.toResponseDto(product);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ProductResponseDto> getProducts(int page, int size, String sortBy, String direction) {
		validatePagination(page, size);

		Pageable pageable = createPageable(page, size, sortBy, direction);

		log.debug("Fetching products: page={}, size={}, sortBy={}, direction={}", page, size, sortBy, direction);

		return productRepository.findAll(pageable).map(productMapper::toResponseDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ProductResponseDto> searchByCategory(String category, int page, int size, String sortBy,
			String direction) {
		validatePagination(page, size);

		Pageable pageable = createPageable(page, size, sortBy, direction);

		log.info("Searching products by category: {}", category);

		return productRepository.findByCategoryIgnoreCase(category, pageable).map(productMapper::toResponseDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ProductResponseDto> searchByName(String name, int page, int size, String sortBy, String direction) {
		validatePagination(page, size);

		Pageable pageable = createPageable(page, size, sortBy, direction);

		log.info("Searching products by name: {}", name);

		return productRepository.findByNameContainingIgnoreCase(name, pageable).map(productMapper::toResponseDto);
	}

	@Override
	public ProductResponseDto updateProduct(Long id, ProductRequestDto requestDto) throws ObjectOptimisticLockingFailureException {
		log.info("Updating product with id: {}", id);

		Product product = findProduct(id);

		try {
			productMapper.updateEntity(requestDto, product);

			Product updatedProduct = productRepository.save(product);

			log.info("Product updated successfully: id={}, newVersion={}", updatedProduct.getId(),
					updatedProduct.getVersion());

			return productMapper.toResponseDto(updatedProduct);

		} catch (OptimisticLockingFailureException ex) {

			log.error("Optimistic locking failed while updating product id={}", id, ex);

			throw ex;
		}
	}

	@Override
	public void deleteProduct(Long id) {
		log.info("Deleting product with id: {}", id);

		Product product = findProduct(id);
		productRepository.delete(product);

		log.info("Product deleted successfully: {}", id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductResponseDto> findByStatus(ProductStatus status) {
		log.debug("Finding products by status: {}", status);

		return productRepository.findProductsByStatus(status).stream().map(productMapper::toResponseDto).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductResponseDto> findAvailableProductsAbovePrice(BigDecimal minPrice) {
		log.debug("Finding available products with minimum price: {}", minPrice);

		return productRepository.findAvailableProductsAbovePrice(minPrice).stream().map(productMapper::toResponseDto)
				.toList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductResponseDto> findLowStockProducts(Integer quantity) {
		log.debug("Finding low-stock products with quantity <= {}", quantity);

		return productRepository.findLowStockProducts(quantity).stream().map(productMapper::toResponseDto).toList();
	}

	private Product findProduct(Long id) {
		return productRepository.findById(id).orElseThrow(() -> {
			log.warn("Product not found with id: {}", id);
			return new ProductNotFoundException("Product not found with id: " + id);
		});
	}

	private Pageable createPageable(int page, int size, String sortBy, String direction) {
		Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

		Sort sort = Sort.by(sortDirection, sortBy);

		return PageRequest.of(page, size, sort);
	}

	private void validatePagination(int page, int size) {
		if (page < 0) {
			throw new IllegalArgumentException("Page must be >= 0");
		}

		if (size < 1 || size > 100) {
			throw new IllegalArgumentException("Size must be between 1 and 100");
		}
	}
}
