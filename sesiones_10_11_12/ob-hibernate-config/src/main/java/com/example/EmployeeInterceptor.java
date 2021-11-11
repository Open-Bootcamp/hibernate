package com.example;

import com.example.entities.Employee;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import java.io.Serializable;

public class EmployeeInterceptor extends EmptyInterceptor {

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {

        if(entity instanceof Employee){
            System.out.println("Guardando empleado!!");
            ((Employee) entity).setLastName("Editado desde interceptor");

            for (int i = 0; i < propertyNames.length; i++) {
                System.out.println(propertyNames[i]);
            }
        }



        return super.onSave(entity, id, state, propertyNames, types);
    }
}
