package com.example.relationships;

import com.example.dao.EmployeeDAO;
import com.example.dao.EmployeeDAOImpl;
import com.example.entities.Car;
import com.example.entities.Employee;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class OneToManyTest {

    @Test
    void oneToManyTest() {

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

        employee.getCars().add(car1);
        employee.getCars().add(car2);
        employee.getCars().add(car3);

        EmployeeDAO dao = new EmployeeDAOImpl();
        dao.create(employee);

        // org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: com.example.entities.Employee.cars, could not initialize proxy - no Session
//        Employee employeeDB = dao.findById(1L);
//        System.out.println(employeeDB);
//        List<Car> cars = employeeDB.getCars();
//        System.out.println(cars);

        // Para evitar LazyInitializationException creamos queries específicas que carguen los datos que queramos
        Employee employeeDB = dao.findByIdEager(1L);
        System.out.println(employeeDB);
        List<Car> cars = employeeDB.getCars();
        System.out.println(cars);
    }
}
