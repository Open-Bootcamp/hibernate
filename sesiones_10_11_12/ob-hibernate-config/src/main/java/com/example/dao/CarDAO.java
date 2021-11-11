package com.example.dao;

import com.example.entities.Car;
import com.example.entities.Employee;

public interface CarDAO {

    Car findById(Long id);

    Car create(Car car);

    boolean deleteById(Long id);
}
