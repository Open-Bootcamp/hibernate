package dao;


import entities.Employee;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import util.HibernateUtil;

import javax.persistence.PersistenceException;
import javax.persistence.criteria.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {


    @Override
    public List<Employee> findAll() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        // Consulta HQL
        Query<Employee> query = session.createQuery("from Employee", Employee.class);
        List<Employee> employees = query.list();
        // equivalente:
        // List<Employee> employees = session.createQuery("from Employee", Employee.class).list();
        session.close();

        return employees;
    }

    @Override
    public Employee create(Employee employee) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            session.save(employee);
            session.getTransaction().commit();
        }catch (PersistenceException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.close();
        return employee;
    }


}
