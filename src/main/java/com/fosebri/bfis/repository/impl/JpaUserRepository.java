package com.fosebri.bfis.repository.impl;

import com.fosebri.bfis.entity.User;
import com.fosebri.bfis.repository.UserRepository;
import com.fosebri.bfis.secutity.service.BCryptPasswordHash;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@Transactional
public class JpaUserRepository implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private BCryptPasswordHash passwordHasher;

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            entityManager.persist(user);
            return user;
        } else {
            return entityManager.merge(user);
        }
    }

    @Override
    public void changePassword(UUID id, String newPassword, User user) {
        user.setPassword(newPassword, passwordHasher);
        entityManager.merge(user);
    }

    @Override
    public void changeStatus(UUID id, boolean status, User user) {
        user.setEnabled(status);
        entityManager.merge(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst();
    }

    @Override
    public Optional<User> findById(UUID id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public Optional<User> findByRefreshToken(UUID refreshToken) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.refreshToken = :refreshToken", User.class)
                .setParameter("refreshToken", refreshToken)
                .getResultStream()
                .findFirst();
    }

    @Override
    public List<User> findUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class)
                .getResultList();
    }

    @Override
    public boolean existsByUsername(String username) {
        return entityManager.createQuery("SELECT COUNT(u) FROM User u WHERE u.username = :username", Long.class)
                .setParameter("username", username)
                .getSingleResult() > 0;
    }

    @Override
    public User getReferenceById(UUID id) {
        return entityManager.getReference(User.class, id);
    }
}
