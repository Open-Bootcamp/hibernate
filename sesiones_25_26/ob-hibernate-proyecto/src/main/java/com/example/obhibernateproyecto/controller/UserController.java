package com.example.obhibernateproyecto.controller;

import com.example.obhibernateproyecto.dao.UserDAO;
import com.example.obhibernateproyecto.entities.User;
import com.example.obhibernateproyecto.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TODO - crear servicio
 * Método actualizar
 * Pruebas de guardado cuando hay asociaciones
 *  - la asociación billinginfo se guarda desde aquí
 *  - la asociación tasks se guarda desde el controlador tasks
 */
@RestController
public class UserController {

    private UserRepository userRepository;

    private UserDAO userDao;

    public UserController(UserRepository userRepository, UserDAO userDao){
        this.userRepository = userRepository;
        this.userDao = userDao;

    }

    @GetMapping("/api/users")
    private List<User> findAll(){
        return this.userRepository.findAll();
    }

    @GetMapping("/api/users/active")
    private List<User> findAllActive(){
        return this.userDao.findAllActive();
    }

    @PostMapping("/api/users")
    private User create(@RequestBody User user){
        return this.userRepository.save(user);
    }

    @PutMapping("/api/users")
    private User update(@RequestBody User user){
        return this.userRepository.save(user);
    }

    @DeleteMapping("/api/users/{id}")
    private void delete(@PathVariable Long id){
        if(this.userRepository.existsById(id))
            this.userRepository.deleteById(id); // TODO revisar integridad referencial
    }

}
