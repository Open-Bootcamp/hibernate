package com.example.dao;

import com.example.entities.Car;
import com.example.entities.Employee;
import com.example.util.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.PersistenceException;

public class CarDAOImpl implements CarDAO{


    @Override
    public Car create(Car car) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            session.save(car);
            session.getTransaction().commit();
        }catch (PersistenceException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.close();
        return car;
    }

    @Override
    public boolean deleteById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();

            Car car = this.findById(id);

            session.delete(car);

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

    @Override
    public Car findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Car car = session.find(Car.class, id);
        session.close();

        return car;
    }
}
