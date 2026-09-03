package com.example.employee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.employee.dto.UserRequestDto;
import com.example.employee.dto.UserResponseDto;
import com.example.employee.entity.Users;

@Mapper(componentModel = "spring")
public interface UserMapper {

	Users toEntity(UserRequestDto dto);

	UserResponseDto toResponseDto(Users user);

	void updateEntity(UserRequestDto dto, @MappingTarget Users user);
}