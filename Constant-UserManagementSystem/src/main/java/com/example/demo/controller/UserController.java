package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.demo.constant.AppConstants;
import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	// CREATE
	@PostMapping
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto dto) {

		UserDto createdUser = userService.createUser(dto);

		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}

	// GET ALL
	@GetMapping
	public ResponseEntity<List<UserDto>> getAllUsers() {

		return ResponseEntity.ok(userService.getAllUsers());
	}

	// GET BY ID
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {

		return ResponseEntity.ok(userService.getUserById(id));
	}

	// UPDATE
	@PutMapping("/{id}")
	public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto dto) {

		return ResponseEntity.ok(userService.updateUser(id, dto));
	}

	// DELETE
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id) {

		userService.deleteUser(id);

		return ResponseEntity.ok(AppConstants.USER_DELETED);
	}

	// SEARCH BY EMAIL
	@GetMapping("/email/{email}")
	public ResponseEntity<UserDto> getByEmail(@PathVariable String email) {

		return ResponseEntity.ok(userService.getUserByEmail(email));
	}

	// SEARCH BY CITY
	@GetMapping("/city/{city}")
	public ResponseEntity<List<UserDto>> getByCity(@PathVariable String city) {

		return ResponseEntity.ok(userService.getUsersByCity(city));
	}

	// SEARCH BY NAME
	@GetMapping("/search")
	public ResponseEntity<List<UserDto>> searchByName(@RequestParam String name) {

		return ResponseEntity.ok(userService.searchByName(name));
	}

	// SALARY GREATER THAN
	@GetMapping("/salary")
	public ResponseEntity<List<UserDto>> getBySalary(@RequestParam Double amount) {

		return ResponseEntity.ok(userService.getUsersBySalary(amount));
	}

	// CITY + SALARY
	@GetMapping("/filter")
	public ResponseEntity<List<UserDto>> getByCityAndSalary(@RequestParam String city, @RequestParam Double salary) {

		return ResponseEntity.ok(userService.getUsersByCityAndSalary(city, salary));
	}
}
