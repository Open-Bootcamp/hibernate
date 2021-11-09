package com.example.dao;

import com.example.entities.Direction;
import com.example.entities.Employee;

import java.util.List;

public interface DirectionDAO {

    List<Direction> findAll();

    Direction findById(Long id);

    Direction create(Direction direction);

    Direction update(Direction direction);

    boolean deleteById(Long id);
}
