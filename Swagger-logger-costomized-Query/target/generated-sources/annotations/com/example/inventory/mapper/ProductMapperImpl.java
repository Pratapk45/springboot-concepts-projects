package com.example.inventory.mapper;

import com.example.inventory.dto.ProductRequestDto;
import com.example.inventory.dto.ProductResponseDto;
import com.example.inventory.entity.Product;
import com.example.inventory.entity.ProductStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-03T22:28:41+0530",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.46.0.v20260528-0407, environment: Java 25.0.3 (Eclipse Adoptium)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product toEntity(ProductRequestDto requestDto) {
        if ( requestDto == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.category( requestDto.category() );
        product.description( requestDto.description() );
        product.name( requestDto.name() );
        product.price( requestDto.price() );
        product.quantity( requestDto.quantity() );
        product.status( requestDto.status() );

        return product.build();
    }

    @Override
    public ProductResponseDto toResponseDto(Product product) {
        if ( product == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String description = null;
        String category = null;
        BigDecimal price = null;
        Integer quantity = null;
        ProductStatus status = null;
        LocalDateTime createdDate = null;
        LocalDateTime updatedDate = null;
        String createdBy = null;
        String updatedBy = null;
        Long version = null;

        id = product.getId();
        name = product.getName();
        description = product.getDescription();
        category = product.getCategory();
        price = product.getPrice();
        quantity = product.getQuantity();
        status = product.getStatus();
        createdDate = product.getCreatedDate();
        updatedDate = product.getUpdatedDate();
        createdBy = product.getCreatedBy();
        updatedBy = product.getUpdatedBy();
        version = product.getVersion();

        ProductResponseDto productResponseDto = new ProductResponseDto( id, name, description, category, price, quantity, status, createdDate, updatedDate, createdBy, updatedBy, version );

        return productResponseDto;
    }

    @Override
    public void updateEntity(ProductRequestDto requestDto, Product product) {
        if ( requestDto == null ) {
            return;
        }

        product.setCategory( requestDto.category() );
        product.setDescription( requestDto.description() );
        product.setName( requestDto.name() );
        product.setPrice( requestDto.price() );
        product.setQuantity( requestDto.quantity() );
        product.setStatus( requestDto.status() );
    }
}
