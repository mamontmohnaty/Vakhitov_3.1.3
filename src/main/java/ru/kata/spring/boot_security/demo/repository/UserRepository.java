package ru.kata.spring.boot_security.demo.repository;


import org.springframework.stereotype.Repository;

import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

@Repository
public class UserRepository implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User getUser(String name) {

        Query query = entityManager.createQuery
                ("SELECT u FROM User u WHERE u.name = :name");
        query.setParameter("name", name);

        return (User) query.getSingleResult();
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("from User", User.class ).getResultList();
    }

    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void saveUser(User user) {
         entityManager.persist(user);
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void deleteById(Long id) {
        entityManager.remove(findById(id));
    }
}
