package com.example.entities;

import com.example.dao.CarDAO;
import com.example.dao.CarDAOImpl;
import com.example.dao.EmployeeDAO;
import com.example.dao.EmployeeDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

/**
 * REVTYPE
 * 0 - INSERT
 * 1 - UPDATE
 * 2 - DELETE
 */
public class EmployeeEnversTest {

    EmployeeDAO employeeDao;
    CarDAO carDao;

    @BeforeEach
        // se ejecuta antes de cada test
    void setUp() {
        employeeDao = new EmployeeDAOImpl();
        carDao = new CarDAOImpl();
    }

    @Test
    void createEmployee() {

        Employee employee = new Employee(null,
                "Empleado4",
                "García",
                "empleado4@company.com",
                32,
                40000d,
                true,
                LocalDate.of(1990, 1, 1),
                null
        );

        employeeDao.create(employee);

        employee.setAge(30);
        employee.setSalary(50000d);

        employeeDao.update(employee);

        Car car1 = new Car(null, "Ford", 1.2, 2005);
        Car car2 = new Car(null, "Alfa", 2.4, 1998);
        Car car3 = new Car(null, "Toyota", 1.8, 2010);
        carDao.create(car1);
        carDao.create(car2);
        carDao.create(car3);

        employee.getCars().add(car1);
        employee.getCars().add(car2);
        employee.getCars().add(car3);

        employee.setSalary(70000d);
        employeeDao.update(employee);

        employee.getCars().clear();

        employee.getCars().add(car3);

        employee.setSalary(80000d);
        employeeDao.update(employee);
    }
}
