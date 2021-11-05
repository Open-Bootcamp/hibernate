package com.example.dao;

import com.example.entities.Employee;
import com.example.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
    public Employee findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Employee employee = session.find(Employee.class, id);
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
    public List<Employee> findByAge(Integer age) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query<Employee> query = session.createQuery("from Employee where age = :age", Employee.class);
        query.setParameter("age", age);
        List<Employee> employees = query.list();

        session.close();
        return employees;

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
