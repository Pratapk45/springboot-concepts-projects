package com.example.employee.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.employee.dto.UserRequestDto;
import com.example.employee.dto.UserResponseDto;
import com.example.employee.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping
	public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto dto) {

		return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(dto));
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {

		return ResponseEntity.ok(userService.getUserById(id));
	}

	@GetMapping("/all")
	public ResponseEntity<List<UserResponseDto>> getAllUsers() {

		return ResponseEntity.ok(userService.getAllUsers());
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDto dto) {

		return ResponseEntity.ok(userService.updateUser(id, dto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id) {

		userService.deleteUser(id);

		return ResponseEntity.ok("User deleted successfully");
	}

	@GetMapping
	public ResponseEntity<Page<UserResponseDto>> getUsers(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String direction) {

		return ResponseEntity.ok(userService.getUsers(page, size, sortBy, direction));
	}

	@GetMapping("/department/{department}")
	public ResponseEntity<Page<UserResponseDto>> getByDepartment(@PathVariable String department,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {

		return ResponseEntity.ok(userService.searchByDepartment(department, page, size));
	}
}