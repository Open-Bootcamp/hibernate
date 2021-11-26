package dao;

import entities.Employee;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeDAOImplTest {

    @Test
    void saveEmployees() {

        // Sin optimización de batch
        // 50000 14s
        // 200000 37s

        // Con optimización de batch
        // 50000 7s
        // 200000 14s

        // Con optimización de batch sin logs sql activados
        // 200000 7s

        EmployeeDAO dao = new EmployeeDAOImpl();

        List<Employee> employees = new ArrayList<>();
        for(int i = 0; i < 200_000; i++)
            employees.add(new Employee(null, "emp", "lastname", "email"));


        dao.saveEmployees(employees);
    }
}