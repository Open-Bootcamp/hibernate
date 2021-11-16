package com.example.springboothibernate.dao;

import com.example.springboothibernate.dto.EmployeeDTO;
import com.example.springboothibernate.entities.Employee;

import java.util.List;

public interface EmployeeDAO {

    List<Employee> findAll();

    List<EmployeeDTO> findAllDTO();
}
