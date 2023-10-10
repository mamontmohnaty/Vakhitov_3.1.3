package ru.kata.spring.boot_security.demo.repository;


import org.springframework.stereotype.Repository;

import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

@Repository
public class RoleRepository implements RoleDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Role> findAll() {
        return entityManager.createQuery("SELECT u FROM User u", Role.class)
                .getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Role> getRoleByName(String name) {
        Query query = entityManager.createQuery
        ("SELECT r FROM Role r WHERE r.name = :name");
        query.setParameter("name", name);

        return (List<Role>) query.getResultList();
    }
}
