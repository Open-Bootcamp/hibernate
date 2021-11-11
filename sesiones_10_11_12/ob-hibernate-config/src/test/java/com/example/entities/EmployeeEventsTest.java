package com.example.entities;

import com.example.dao.CarDAO;
import com.example.dao.CarDAOImpl;
import com.example.dao.EmployeeDAO;
import com.example.dao.EmployeeDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EmployeeEventsTest {

    EmployeeDAO employeeDao;
    CarDAO carDao;

    @BeforeEach
        // se ejecuta antes de cada test
    void setUp() {
        employeeDao = new EmployeeDAOImpl();
        carDao = new CarDAOImpl();
    }

    @Test
    void prePersist() {

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

        /*
        Verificar en los logs que se invoca el método prePersist y se asigna una
        fecha al campo registerDate.
         */
    }

    @Test
    void preUpdate() {
        Employee employee = employeeDao.findById(1L);

        employee.setAge(99);

        employeeDao.update(employee);

        employee.setMarried(true);

        employeeDao.update(employee);
        /*
        Verificar en los logs que se ha ejecutado el método preUpdate()
         */
    }

    @Test
    void preRemoveEmployee() {

        Employee employee = new Employee(null,
                "Empleado one to many",
                "García",
                "empleadoOneToMany@company.com",
                32,
                40000d,
                true,
                LocalDate.of(1990, 1, 1),
                LocalDateTime.now()
        );

        Car car1 = new Car(null, "Ford", 1.2, 2005);
        Car car2 = new Car(null, "Alfa", 2.4, 1998);
        Car car3 = new Car(null, "Toyota", 1.8, 2010);
        carDao.create(car1);
        carDao.create(car2);
        carDao.create(car3);

        employee.getCars().add(car1);
        employee.getCars().add(car2);
        employee.getCars().add(car3);

        employeeDao.create(employee);

        employeeDao.deleteById(employee.getId());
    }

    @Test
    void preRemoveCar() {

        Employee employee = new Employee(null,
                "Empleado one to many",
                "García",
                "empleadoOneToMany@company.com",
                32,
                40000d,
                true,
                LocalDate.of(1990, 1, 1),
                LocalDateTime.now()
        );

        Car car1 = new Car(null, "Ford", 1.2, 2005);
        Car car2 = new Car(null, "Alfa", 2.4, 1998);
        Car car3 = new Car(null, "Toyota", 1.8, 2010);
        carDao.create(car1);
        carDao.create(car2);
        carDao.create(car3);

        employee.getCars().add(car1);
        employee.getCars().add(car2);
        employee.getCars().add(car3);

        employeeDao.create(employee);

        carDao.deleteById(car1.getId());
    }
}
