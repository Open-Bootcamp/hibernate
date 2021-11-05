package com.example.dao;

import com.example.entities.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Tests para las operaciones Criteria
 */
class EmployeeCriteriaTest {

    EmployeeDAO dao;

    @BeforeEach
    void setUp() {
        dao = new EmployeeDAOImpl();
    }

    @Test
    void findAllCriteria() {
        List<Employee> employees = dao.findAllCriteria();
        System.out.println(employees);
    }
    @Test
    void findByIdCriteria() {
        Employee employee = dao.findByIdCriteria(1L);
        System.out.println(employee);
    }

    @Test
    void findByLastNameLikeCriteria() {
        // List<Employee> employees = dao.findByLastNameLikeCriteria("Val");
        List<Employee> employees = dao.findByLastNameLikeCriteria("Castro");
        System.out.println(employees);
    }

    @Test
    void findByAgeGreaterCriteria() {
        List<Employee> employees = dao.findByAgeGreaterCriteria(19);
        System.out.println(employees);
    }

    @Test
    void findByAgeBetweenCriteria() {
        List<Employee> employees = dao.findByAgeBetweenCriteria(19, 25);
        System.out.println(employees);
    }


}