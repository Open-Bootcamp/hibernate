package com.example.entities;

import com.example.dao.DirectionDAO;
import com.example.dao.DirectionDAOImpl;
import com.example.dao.EmployeeDAO;
import com.example.dao.EmployeeDAOImpl;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmployeeTest {

    @Test
    void createTablesTest(){


        Employee employee1 = new Employee(null,
                "Empleado3",
                "García",
                "empleado3@company.com",
                32,
                40000d,
                true,
                LocalDate.of(1990, 1, 1),
                LocalDateTime.now()
                );

        Employee employee2 = new Employee(null,
                "Empleado4",
                "Perez",
                "empleado4@company.com",
                50,
                60000d,
                true,
                LocalDate.of(1950, 1, 1),
                LocalDateTime.now()
        );
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        session.save(employee1);
        session.save(employee2);

        session.getTransaction().commit();

        session.close();
        sessionFactory.close();
        HibernateUtil.shutdown();
    }


    @Test
    void nickNamesTest() {
        Employee employee = new Employee(null,
                "Empleado junit nicknames",
                "García",
                "empleado4@company.com",
                32,
                40000d,
                true,
                LocalDate.of(1990, 1, 1),
                LocalDateTime.now()
        );

        // opcion 1
//        List<String> nickNames = new ArrayList<>();
//        nickNames.add("nickname1");
//        nickNames.add("nickname2");
//        nickNames.add("nickname3");
//        employee.setNickNames(nickNames);

        // opcion 2
        employee.getNickNames().add("nicknames1");
        employee.getNickNames().add("nicknames2");
        employee.getNickNames().add("nicknames3");
        employee.getNickNames().add("nicknames3");
        employee.getPostalCodes().add(33010);
        employee.getPostalCodes().add(88030);
        employee.getPostalCodes().add(21040);
        employee.getCreditCards().add("1234-1234-1234-1234");
        employee.getCreditCards().add("1234-1234-1234-4567");
        employee.getCreditCards().add("1234-1234-1234-7892");
        employee.getPhones().put("654321123", "Orange");
        employee.getPhones().put("655777876", "Jazztel");
        employee.getPhones().put("632111877", "Movistar");


        for (Map.Entry<String, String> phone : employee.getPhones().entrySet()){
            System.out.print("Num telefono: " + phone.getKey());
            System.out.println(" Operador: " + phone.getValue());
        }

//        employee.setCategory(EmployeeCategory.JUNIOR); // 0
//        employee.setCategory(EmployeeCategory.SENIOR); // 1
//        employee.setCategory(EmployeeCategory.ANALYST); // 2
        employee.setCategory(EmployeeCategory.C_LEVEL); // 3

        EmployeeDAO dao = new EmployeeDAOImpl();
        dao.create(employee);

    }


}
