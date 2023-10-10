package ru.kata.spring.boot_security.demo.repository;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {
    List<User> findAll();

    User findById(Long id);

    void saveUser(User user);

    void updateUser(User user);

    void deleteById(Long id);

    User getUser(String name);
}