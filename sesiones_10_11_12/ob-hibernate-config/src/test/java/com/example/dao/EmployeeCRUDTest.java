package com.example.dao;

import com.example.entities.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para las operaciones CRUD
 */
class EmployeeCRUDTest {

    EmployeeDAO dao;

    @BeforeEach
    void setUp() {
        dao = new EmployeeDAOImpl();
    }

    @Test
    void findAll() {
        List<Employee> employees = dao.findAll();
        System.out.println(employees);
    }

    @Test
    void findById() {
        Employee employee1 = dao.findById(1L);
        Employee employee2 = dao.findById(2L);
        Employee employee3 = dao.findById(3L);
    }

    @Test
    void findByAge() {
        List<Employee> employees35 = dao.findByAge(35);
        List<Employee> employees20 = dao.findByAge(20);
        List<Employee> employees50 = dao.findByAge(50);
    }

    @Test
    void create() {
        Employee employee = new Employee(null, "Empleado creado desde JUnit", "Test", "test@test.com", 18, 20000d, false, LocalDate.now(), LocalDateTime.now());
        employee = dao.create(employee);
        System.out.println(employee);
    }

    @Test
    void update() {
        // le ponemos id porque ahora es una actualización no creación
        Employee employee1 = new Employee(1L,
                "Empleado 1 editado",
                "García",
                "empleado1@company.com",
                32,
                40000d,
                true,
                LocalDate.of(1990, 1, 1),
                LocalDateTime.now()
        );
        employee1 = dao.update(employee1);
        System.out.println(employee1);
    }

    @Test
    void deleteById() {
        boolean result = dao.deleteById(1L);
        System.out.println(result);

    }

    @Test
    void countTest() {

        Long totalEmployees = dao.count();
        System.out.println("El número de empleados es: " + totalEmployees);
    }
}