package com.example.relationships;

import com.example.dao.DirectionDAO;
import com.example.dao.DirectionDAOImpl;
import com.example.dao.EmployeeDAO;
import com.example.dao.EmployeeDAOImpl;
import com.example.entities.Direction;
import com.example.entities.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OneToOneTest {

    @Test
    @DisplayName("Test para probar la asociación One To One entre Employee y Direction")
    void employeeDirectionTest(){

        Direction direction = new Direction(null, "ELM street", "Cansas", "Croacia");

        Employee employee = new Employee(null,
                "Empleado one to one",
                "García",
                "empleadoOneToOne@company.com",
                32,
                40000d,
                true,
                LocalDate.of(1990, 1, 1),
                LocalDateTime.now()
        );

        employee.setDirection(direction); // One To One

        EmployeeDAO employeeDAO = new EmployeeDAOImpl();
        DirectionDAO directionDAO = new DirectionDAOImpl();

        directionDAO.create(direction);
        employeeDAO.create(employee);

        // asegurarse recuperando de nuevo el empleado de base de datos
        Employee employeeDB = employeeDAO.findById(1L);
        System.out.println(employeeDB.getDirection());
    }
}
