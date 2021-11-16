package com.example.springboothibernate.dao;

import com.example.springboothibernate.dto.EmployeeDTO;
import com.example.springboothibernate.entities.Employee;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * JPA
 * Hibernate
 */
@Repository
public class EmployeeDAOImpl implements EmployeeDAO{

    private Session session; // Hibernate

    @PersistenceContext
    private EntityManager entityManager; // JPA

    public EmployeeDAOImpl(Session session){
        this.session = session;
    }

    @Override
    public List<Employee> findAll() {
        long start = System.currentTimeMillis();
        List<Employee> employees = session.createQuery("from Employee", Employee.class).list();
        long end = System.currentTimeMillis();
        System.out.println("Tiempo total findAll(): " + (end - start) + " ms");
        return employees;
    }

    @Override
    public List<EmployeeDTO> findAllDTO() {
        long start = System.currentTimeMillis();
        List<EmployeeDTO> employees =
                session.createQuery("SELECT new com.example.springboothibernate.dto.EmployeeDTO(e.id, e.email) from Employee e").list();
        long end = System.currentTimeMillis();
        System.out.println("Tiempo total findAllDTO(): " + (end - start) + " ms");
        return employees;
    }
}
