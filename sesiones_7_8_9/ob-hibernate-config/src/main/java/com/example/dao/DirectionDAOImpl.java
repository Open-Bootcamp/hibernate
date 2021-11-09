package com.example.dao;

import com.example.entities.Direction;
import com.example.entities.Employee;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.PersistenceException;
import java.util.List;

public class DirectionDAOImpl implements DirectionDAO {

    @Override
    public List<Direction> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query<Direction> query = session.createQuery("from Direction", Direction.class);
        List<Direction> directions = query.list();

        session.close();

        return directions;
    }

    @Override
    public Direction findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Direction direction = session.find(Direction.class, id);
        session.close();

        return direction;
    }

    @Override
    public Direction create(Direction direction) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            session.save(direction);
            session.getTransaction().commit();
        }catch (PersistenceException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.close();
        return direction;
    }

    @Override
    public Direction update(Direction direction) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            session.update(direction);
            session.getTransaction().commit();
        }catch (PersistenceException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.close();
        return direction;
    }

    @Override
    public boolean deleteById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();

            Direction direction = this.findById(id);

            session.delete(direction);

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
