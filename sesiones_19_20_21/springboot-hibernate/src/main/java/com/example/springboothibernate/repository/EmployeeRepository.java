package com.example.springboothibernate.repository;

import com.example.springboothibernate.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
