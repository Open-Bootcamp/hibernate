package com.example.dao;

import com.example.dto.EmployeeDTO;
import com.example.entities.Employee;
import org.junit.jupiter.api.Test;

public class NativeQueryTest {

    @Test
    void findByIdNative() {

        EmployeeDAO dao = new EmployeeDAOImpl();
        Employee result = dao.findByIdNative(1L);
        System.out.println(result);
    }
}
