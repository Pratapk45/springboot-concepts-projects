package com.example.employee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.employee.dto.LoginRequestDto;
import com.example.employee.dto.LoginResponseDto;
import com.example.employee.dto.UserRequestDto;
import com.example.employee.dto.UserResponseDto;
import com.example.employee.entity.Users;
import com.example.employee.exception.DuplicateEmailException;
import com.example.employee.exception.UserNotFoundException;
import com.example.employee.mapper.UserMapper;
import com.example.employee.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserMapper userMapper;

	@Override
	public UserResponseDto createUser(UserRequestDto dto) {

		if (userRepository.existsByEmail(dto.getEmail())) {
			throw new DuplicateEmailException("Email already exists: " + dto.getEmail());
		}

		Users user = userMapper.toEntity(dto);

		Users savedUser = userRepository.save(user);

		return userMapper.toResponseDto(savedUser);
	}

	@Override
	@Transactional(readOnly = true)
	public UserResponseDto getUserById(Long id) {

		Users user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

		return userMapper.toResponseDto(user);
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserResponseDto> getAllUsers() {

		return userRepository.findAll().stream().map(userMapper::toResponseDto).toList();
	}

	
	@Transactional
	@Override
	public UserResponseDto updateUser(Long id, UserRequestDto dto) {

		Users user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

		userMapper.updateEntity(dto, user);

		return userMapper.toResponseDto(user);
	}

	@Override
	public void deleteUser(Long id) {

		Users user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

		userRepository.delete(user);
	}

	@Override
	@Transactional(readOnly = true)
	public LoginResponseDto login(LoginRequestDto dto) {

		Users user = userRepository.findByEmailAndPassword(dto.getEmail(), dto.getPassword())
				.orElseThrow(() -> new UserNotFoundException("Invalid email or password"));

		return LoginResponseDto.builder().userId(user.getId()).name(user.getName()).email(user.getEmail())
				.message("Login successful").build();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<UserResponseDto> getUsers(int page, int size, String sortBy, String direction) {

		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

		Pageable pageable = PageRequest.of(page, size, sort);

		return userRepository.findAll(pageable).map(userMapper::toResponseDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<UserResponseDto> searchByDepartment(String department, int page, int size) {

		Pageable pageable = PageRequest.of(page, size);

		return userRepository.findByDepartment(department, pageable).map(userMapper::toResponseDto);
	}
}