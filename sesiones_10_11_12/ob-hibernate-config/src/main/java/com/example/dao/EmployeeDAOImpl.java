package com.example.dao;

import com.example.dto.EmployeeDTO;
import com.example.entities.Employee;
import com.example.entities.EmployeeCategory;
import com.example.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

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
    public List<Employee> findAllCriteria() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        // 1. criteria
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> criteria = builder.createQuery(Employee.class);
        criteria.select(criteria.from(Employee.class));

        // 2. query
        List<Employee> employees = session.createQuery(criteria).list();

        session.close();
        return employees;
    }

    @Override
    public List<Employee> findAllNative() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Employee> employees = session.createNativeQuery("SELECT * FROM ob_employees", Employee.class).list();

        session.close();
        return employees;
    }

    @Override
    public List<Employee> findMostPaid() {
        Session session = HibernateUtil.getSessionFactory().openSession();

         List<Employee> employees = session.createNamedQuery("Employee.mostPaid", Employee.class).list();

         session.close();

        return employees;
    }

    @Override
    public List<EmployeeDTO> findAllProjection() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<EmployeeDTO> employeeDtos = new ArrayList<>();
        List<Object[]> employees = session.createNativeQuery("SELECT id, email from ob_employees").list();
        for (Object[] employee: employees) {
            Long id = ((BigInteger) employee[0]).longValue();
            String email = (String) employee[1];
            employeeDtos.add(new EmployeeDTO(id, email));
        }

        session.close();
        return employeeDtos;
    }

    @Override
    public Long count() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Long count = (Long) session.createQuery("select count(e) from Employee e").getSingleResult();

        session.close();
        return count;
    }

    @Override
    public Employee findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Employee employee = session.find(Employee.class, id);
        session.close();

        return employee;
    }

    @Override
    public Employee findByIdEager(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();


        Query<Employee> query = session.createQuery("select distinct e from Employee e join fetch e.cars where e.id = :pk", Employee.class);
        query.setParameter("pk", id);

        Employee employee = query.getSingleResult();

        session.close();

        return employee;
    }

    @Override
    public Employee findByIdCriteria(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        // 1. criteria
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> criteria = builder.createQuery(Employee.class);
        Root<Employee> root = criteria.from(Employee.class);

        Predicate filter = builder.equal(root.get("id"), id);

        criteria.select(root).where(filter);

        // 2. query
        Employee employee = session.createQuery(criteria).getSingleResult();

        session.close();
        return employee;
    }

    @Override
    public Employee findByIdNative(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        NativeQuery<Employee> query = session.createNativeQuery("select * from ob_employees where id = :id", Employee.class);
        query.setParameter("id", id);

        Employee employee = query.getSingleResult();

        session.close();
        return employee;
    }


    @Override
    public List<Employee> findByAge(Integer age) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        // Named Parameter
//        Query<Employee> query = session.createQuery("from Employee where age = :age", Employee.class);
//        query.setParameter("age", age);

        // Position Parameters
        Query<Employee> query = session.createQuery("from Employee where age = ?1", Employee.class);
        query.setParameter(1, age);

        List<Employee> employees = query.list();

        session.close();
        return employees;

    }

    /**
     * Operación de agregación
     *
     * @return
     */
    @Override
    public Integer findAvgAgeCriteria() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Double> criteria = builder.createQuery(Double.class);
        Root<Employee> root = criteria.from(Employee.class);


        Expression<Double> avg = builder.avg(root.get("age"));

        criteria.select(avg);

        // 2. query
        Double age = session.createQuery(criteria).getSingleResult();
        return (int)(Math.round(age));

        // Para realizar sub queries
        // criteria.subquery()
    }

    @Override
    public List<Employee> findByLastNameLikeCriteria(String lastName) {

        // equivalente a contains
        // SELECT * FROM ob_employees WHERE lastName like '%ence%'

        Session session = HibernateUtil.getSessionFactory().openSession();

        // 1. criteria
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> criteria = builder.createQuery(Employee.class);
        Root<Employee> root = criteria.from(Employee.class);

        Predicate filter = builder.like(root.get("lastName"), "%" + lastName + "%");

        criteria.select(root).where(filter);

        // 2. query
        List<Employee> employees = session.createQuery(criteria).list();

        session.close();
        return employees;
    }

    @Override
    public List<Employee> findByAgeGreaterCriteria(Integer age) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        // 1. criteria
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> criteria = builder.createQuery(Employee.class);

        Root<Employee> root = criteria.from(Employee.class);

        Predicate filter = builder.gt(root.get("age"), age);

        criteria.select(root).where(filter);

        // 2. query
        List<Employee> employees = session.createQuery(criteria).list();

        session.close();
        return employees;
    }

    @Override
    public List<Employee> findByAgeBetweenCriteria(Integer min, Integer max) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        // 1. criteria
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> criteria = builder.createQuery(Employee.class);
        Root<Employee> root = criteria.from(Employee.class);

        Predicate filter = builder.between(root.get("age"), min, max);

        criteria.select(root).where(filter);

        // 2. query
        List<Employee> employees = session.createQuery(criteria).list();

        session.close();
        return employees;
    }

    @Override
    public List<Employee> findByAgeBetweenCriteriaAndCategory(Integer ageMin, Integer ageMax, EmployeeCategory category) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> criteria = builder.createQuery(Employee.class);

        Root<Employee> root = criteria.from(Employee.class);

        Predicate ageFilter = builder.between(root.get("age"), ageMin, ageMax);
        Predicate categoryFilter = builder.equal(root.get("category"), category);
        Predicate filter = builder.and(ageFilter, categoryFilter); // AND
        // Predicate filter = builder.or(ageFilter, categoryFilter); // OR

        criteria.select(root).where(filter);

        List<Employee> employees = session.createQuery(criteria).list();
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

    @Override
    public Employee update(Employee employee) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            session.update(employee);
            session.getTransaction().commit();
        }catch (PersistenceException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.close();
        return employee;
    }

    @Override
    public boolean deleteById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();

            Employee employee = this.findById(id);

            session.delete(employee);

            session.getTransaction().commit();
        }catch (PersistenceException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        }finally{
            session.close();
        }

        return true;
    }
}
