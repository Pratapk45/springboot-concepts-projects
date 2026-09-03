package com.example.employee.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.employee.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

	Optional<Users> findByEmail(String email);

	Optional<Users> findByEmailAndPassword(String email, String password);

	boolean existsByEmail(String email);

	@Query("""
			SELECT u
			FROM Users u
			WHERE u.department = :department
			""")
	Page<Users> findByDepartment(@Param("department") String department, Pageable pageable);
}