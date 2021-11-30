package com.example.obhibernateproyecto.dao;

import com.example.obhibernateproyecto.entities.User;

import java.util.List;

public interface UserDAO {

    List<User> findAllActive();
}
