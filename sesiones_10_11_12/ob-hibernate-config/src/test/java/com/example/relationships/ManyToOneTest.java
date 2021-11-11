package com.example.relationships;

import com.example.dao.EmployeeDAO;
import com.example.dao.EmployeeDAOImpl;
import com.example.entities.Company;
import com.example.entities.Employee;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ManyToOneTest {

    @Test
    void manyToOneTest() {

        Employee employee1 = new Employee(null,
                "Empleado many to one 1",
                "García",
                "empleadoManytoOne1@company.com",
                32,
                40000d,
                true,
                LocalDate.of(1990, 1, 1),
                LocalDateTime.now()
        );
        Employee employee2 = new Employee(null,
                "Empleado many to one 2",
                "García",
                "empleadoManytoOne2@company.com",
                32,
                40000d,
                true,
                LocalDate.of(1990, 1, 1),
                LocalDateTime.now()
        );

        Company company = new Company(null, "B32424243G", "COSMIC DEVELOPMENTS S.L.", 30500d, 2021);
        employee1.setCompany(company);
        employee2.setCompany(company);

        EmployeeDAO dao = new EmployeeDAOImpl();
        dao.create(employee1);
        dao.create(employee2);

        Employee employeeDB = dao.findById(1L);
        System.out.println(employeeDB.getCompany());

        // Si desde company queremos recuperar la lista de employees hay que hacer un
        // findByIdEager

    }
}
