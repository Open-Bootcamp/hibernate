package com.example.springboothibernate.dao;

import com.example.springboothibernate.dto.EmployeeDTO;
import com.example.springboothibernate.entities.Employee;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
//import javax.transaction.Transactional;
//import javax.persistence.Query;

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

//    @Override
//    public List<Employee> findAll() {
//        long start = System.currentTimeMillis();
//        List<Employee> employees = session.createQuery("from Employee", Employee.class).list();
//        long end = System.currentTimeMillis();
//        System.out.println("Tiempo total findAll(): " + (end - start) + " ms");
//        return employees;
//    }
//
//    @Override
//    public List<EmployeeDTO> findAllDTO() {
//        long start = System.currentTimeMillis();
//        List<EmployeeDTO> employees =
//                session.createQuery("SELECT new com.example.springboothibernate.dto.EmployeeDTO(e.id, e.email) from Employee e").list();
//        long end = System.currentTimeMillis();
//        System.out.println("Tiempo total findAllDTO(): " + (end - start) + " ms");
//        return employees;
//    }

    @Override
    public List<Employee> findAllLastPage() {

        // calcular el numero de página
        int size = 20;
        String countHQL = "select count(e.id) from Employee e";
        Long countResult = (Long) session.createQuery(countHQL).uniqueResult(); // 200000
        int lastPageNum = (int) Math.ceil(countResult / size) ;

        // recuperar empleados de la última página
        Query query = session.createQuery("from Employee");
        query.setFirstResult((lastPageNum - 1) * size);
        query.setMaxResults(size);
        List<Employee> lastPageEmployees = query.list();

        return lastPageEmployees;
    }

    @Transactional
    @Override
    public Employee save(Employee employee) {

        try{
//            session.beginTransaction();

            entityManager.persist(employee);

//            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
        }


        return employee;
    }

}
