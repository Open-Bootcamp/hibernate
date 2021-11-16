package com.example.springboothibernate;

import com.example.springboothibernate.dao.EmployeeDAO;
import com.example.springboothibernate.entities.Employee;
import com.example.springboothibernate.repository.EmployeeRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringbootHibernateApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringbootHibernateApplication.class, args);

        EmployeeRepository repository = context.getBean(EmployeeRepository.class);

//        Employee employee0 = new Employee(null, "Empleado 0", 40, "emp0@company.com");
//        Employee employee1 = new Employee(null, "Empleado 1", 40, "emp1@company.com");
//        List<Employee> employees = new ArrayList<>();
//        for (int i = 0; i < 50000; i++){
//            Employee employeeToInsert = new Employee(null, "Empleado 0", 40, "emp0@company.com");
//            employees.add(employeeToInsert);
//        }
//        repository.save(employee0);
//        repository.save(employee1);
//        repository.saveAll(employees);

        //System.out.println(repository.findAll());

        EmployeeDAO dao = context.getBean(EmployeeDAO.class);
        //System.out.println(dao.findAll());

//        dao.findAll(); // 530ms
        dao.findAllDTO(); // 174ms
    }

}
