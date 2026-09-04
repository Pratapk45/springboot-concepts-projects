package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
	

	Optional<Users> findByEmail(String Email);
	
	List<Users> findByCity(String City);
	
	List<Users> findByName(String Name);
	
	List<Users> findBySalaryGreaterThan(Double Salary);
	
	List<Users> findByCityAndSalaryGreaterThan(String City , Double Salary);

}
