package com.example.relationships;

import com.example.dao.EmployeeDAO;
import com.example.dao.EmployeeDAOImpl;
import com.example.entities.Employee;
import com.example.entities.Project;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ManyToManyTest {

    @Test
    void manyToManyTest() {

        Project project1 = new Project(null, "Project X 1", LocalDate.now());
        Project project2 = new Project(null, "Project X 2", LocalDate.now());

        Employee employee = new Employee(null,
                "Empleado many to many",
                "García",
                "empleadoManyToMany@company.com",
                32,
                40000d,
                true,
                LocalDate.of(1990, 1, 1),
                LocalDateTime.now()
        );
        employee.getProjects().add(project1);
        employee.getProjects().add(project2);

        EmployeeDAO dao = new EmployeeDAOImpl();
        dao.create(employee);
    }
}
