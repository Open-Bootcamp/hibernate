package dao;



import entities.Employee;

import java.util.List;


public interface EmployeeDAO {

    List<Employee> findAll();

    Employee create(Employee employee);


}
