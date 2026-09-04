package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.constant.AppConstants;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Users;
import com.example.demo.exception.DuplicateEmailException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	// CREATE
	public UserDto createUser(UserDto dto) {

		// Optional used for checking duplicate email
		userRepository.findByEmail(dto.getEmail()).ifPresent(user -> {
			throw new DuplicateEmailException(AppConstants.EMAIL_ALREADY_EXISTS + dto.getEmail());
		});

		Users user = Users.builder().name(dto.getName()).email(dto.getEmail()).phone(dto.getPhone()).city(dto.getCity())
				.salary(dto.getSalary()).build();

		Users savedUser = userRepository.save(user);

		return convertToDto(savedUser);
	}

	// GET ALL
	public List<UserDto> getAllUsers() {

		return userRepository.findAll().stream().map(this::convertToDto).toList();
	}

	// GET BY ID
	public UserDto getUserById(Long id) {

		Users user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND + id));

		return convertToDto(user);
	}

	// UPDATE
	public UserDto updateUser(Long id, UserDto dto) {

		Users user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND + id));

		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setPhone(dto.getPhone());
		user.setCity(dto.getCity());
		user.setSalary(dto.getSalary());

		Users updatedUser = userRepository.save(user);

		return convertToDto(updatedUser);
	}

	// DELETE
	public void deleteUser(Long id) {

		Users user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND + id));

		userRepository.delete(user);
	}

	// SEARCH BY EMAIL
	public UserDto getUserByEmail(String email) {

		Users user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

		return convertToDto(user);
	}

	// SEARCH BY CITY
	public List<UserDto> getUsersByCity(String city) {

		return userRepository.findByCity(city).stream().map(this::convertToDto).toList();
	}

	// SEARCH BY NAME
	public List<UserDto> searchByName(String name) {

		return userRepository.findByName(name).stream().map(this::convertToDto).toList();
	}

	// SALARY GREATER THAN
	public List<UserDto> getUsersBySalary(Double salary) {

		return userRepository.findBySalaryGreaterThan(salary).stream().map(this::convertToDto).toList();
	}

	// CITY + SALARY
	public List<UserDto> getUsersByCityAndSalary(String city, Double salary) {

		return userRepository.findByCityAndSalaryGreaterThan(city, salary).stream().map(this::convertToDto).toList();
	}

	// ENTITY -> DTO
	public UserDto convertToDto(Users user) {

		return UserDto.builder().name(user.getName()).email(user.getEmail()).phone(user.getPhone()).city(user.getCity())
				.salary(user.getSalary()).build();
	}
}
