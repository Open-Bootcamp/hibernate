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


        List<Employee> employees = new ArrayList<>();

        for (int i = 0; i < 50000; i++)
            employees.add(new Employee(null, "Empleado " + i, 40, "emp" + i + "@company.com", LocalDate.now(), true, 60000d));

        for (int i = 50000; i < 100000; i++)
            employees.add(new Employee(null, "Empleado " + i, 20, "emp" + i + "@company.com", LocalDate.of(2020, 1, 1), false, 30000d));

        for (int i = 100000; i < 200000; i++)
            employees.add(new Employee(null, "Empleado " + i, 30, "emp" + i + "@company.com", LocalDate.of(2019, 1, 1), true, 40000d));

        repository.saveAll(employees);



    }

}
