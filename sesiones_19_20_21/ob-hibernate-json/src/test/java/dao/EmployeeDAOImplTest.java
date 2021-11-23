package dao;

import entities.Employee;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import util.HibernateUtil;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeDAOImplTest {

    @Test
    void create() {

        Map<String, Object> json = new HashMap<>();
        json.put("color", "azul");
        json.put("number", 40);
        
        Employee empl1 = new Employee(null, "Emp1", "perez", "emp@emp.com", json);

        EmployeeDAO dao = new EmployeeDAOImpl();
        dao.create(empl1);

        System.out.println(dao.findAll());
    }
}