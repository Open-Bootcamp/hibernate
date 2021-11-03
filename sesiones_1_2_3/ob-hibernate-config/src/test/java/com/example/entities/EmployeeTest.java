package com.example.entities;

import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    void createTables2Test(){

    }
}
