package com.example.employee.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.employee.dto.LoginRequestDto;
import com.example.employee.dto.LoginResponseDto;
import com.example.employee.dto.UserRequestDto;
import com.example.employee.dto.UserResponseDto;

public interface UserService {

	UserResponseDto createUser(UserRequestDto dto);

	UserResponseDto getUserById(Long id);

	List<UserResponseDto> getAllUsers();

	UserResponseDto updateUser(Long id, UserRequestDto dto);

	void deleteUser(Long id);

	LoginResponseDto login(LoginRequestDto dto);

	Page<UserResponseDto> getUsers(int page, int size, String sortBy, String direction);

	Page<UserResponseDto> searchByDepartment(String department, int page, int size);
}