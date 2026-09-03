package com.example.inventory.mapper;

import com.example.inventory.dto.ProductRequestDto;
import com.example.inventory.dto.ProductResponseDto;
import com.example.inventory.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductRequestDto requestDto);

    ProductResponseDto toResponseDto(Product product);

    void updateEntity(ProductRequestDto requestDto, @MappingTarget Product product);
}
