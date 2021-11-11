package com.example.dao;

import com.example.entities.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class NamedQueryTest {

    EmployeeDAO dao;

    @BeforeEach
        // se ejecuta antes de cada test
    void setUp() {
        dao = new EmployeeDAOImpl();
    }

    @Test
    void findMostPaid() {
        List<Employee> employees = dao.findMostPaid();
        System.out.println(employees);

    }
}
