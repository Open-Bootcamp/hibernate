package com.example.springboothibernate;

import com.example.springboothibernate.dao.EmployeeDAO;
import com.example.springboothibernate.entities.Employee;
import com.example.springboothibernate.repository.EmployeeRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringbootHibernateApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringbootHibernateApplication.class, args);
        EmployeeRepository repository = context.getBean(EmployeeRepository.class);


        // Descomentar para insertar datos demo
//        List<Employee> employees = new ArrayList<>();
//
//        for (int i = 0; i < 50000; i++)
//            employees.add(new Employee(null, "Empleado " + i, 40, "emp" + i + "@company.com", LocalDate.now(), true, 60000d));
//
//        for (int i = 50000; i < 100000; i++)
//            employees.add(new Employee(null, "Empleado " + i, 20, "emp" + i + "@company.com", LocalDate.of(2020, 1, 1), false, 30000d));
//
//        for (int i = 100000; i < 200000; i++)
//            employees.add(new Employee(null, "Empleado " + i, 30, "emp" + i + "@company.com", LocalDate.of(2019, 1, 1), true, 40000d));
//
//        repository.saveAll(employees);

        // insertar 25 nuevos empleados para comprobar la paginación
//        List<Employee> employees = new ArrayList<>();
//        for (int i = 0; i < 25; i++)
//            employees.add(new Employee(null, "Empleado Pagination " + i, 30, "pagination@example.com", LocalDate.of(2019, 1, 1), true, 40000d));
//
//        repository.saveAll(employees);
//
//        EmployeeDAO dao = context.getBean(EmployeeDAO.class);
//        List<Employee> last20Employees = dao.findAllLastPage();
//        System.out.println(last20Employees);

        EmployeeDAO dao = context.getBean(EmployeeDAO.class);
        Employee employeeDB = dao.save(
                new Employee(null, "Empleado transaction ", 30, "transaction@example.com", LocalDate.of(2019, 1, 1), true, 40000d)
        );
        System.out.println(employeeDB);
    }

}
