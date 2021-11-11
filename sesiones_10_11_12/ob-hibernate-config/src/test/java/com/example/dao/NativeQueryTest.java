package com.example.dao;

import com.example.dto.EmployeeDTO;
import com.example.entities.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class NativeQueryTest {

    EmployeeDAO dao;

    @BeforeEach // se ejecuta antes de cada test
    void setUp() {
        dao = new EmployeeDAOImpl();
    }

    @Test
    void findByIdNative() {
        Employee result = dao.findByIdNative(1L);
        System.out.println(result);
    }

    @Test
    void findAllNative() {
        List<Employee> employees = dao.findAllNative();
        System.out.println(employees);
    }

    @Test
    void findAllProjection() {
        List<EmployeeDTO> employees = dao.findAllProjection();
        System.out.println(employees);

    }


}
