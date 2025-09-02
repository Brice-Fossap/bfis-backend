package com.fosebri.bfis.repository.impl;

import com.fosebri.bfis.entity.Person;
import com.fosebri.bfis.repository.PersonRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class JpaPersonRepository implements PersonRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Person person) {
        if (person.getId() == null) {
            entityManager.persist(person);
        } else {
            entityManager.merge(person);
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        return entityManager.createQuery("SELECT COUNT(p) FROM Person p WHERE p.email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult() > 0;
    }
}
