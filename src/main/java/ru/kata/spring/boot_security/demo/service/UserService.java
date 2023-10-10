package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Service
public interface UserService {
    void deleteById(Long id);

    User findUserByName(String name);

    List<User> findAll();

    User findById(Long id);

    void saveUser(User user);

    void updateUser(User user);

}