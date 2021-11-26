package dao;


import entities.Employee;
import org.hibernate.Session;
import util.HibernateUtil;

import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {


    @Override
    public void saveEmployees(List<Employee> employees) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        try{
            // guardar los empleados
            session.beginTransaction();

            for(Employee employee : employees)
                session.save(employee);

            session.getTransaction().commit();
        }catch(RuntimeException e){
            if(session.getTransaction() != null && session.getTransaction().isActive())
                session.getTransaction().rollback();
            throw e;
        } finally {
            if(session != null){
                session.close();
            }
        }

    }
}
