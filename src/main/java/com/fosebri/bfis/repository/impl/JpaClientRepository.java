package com.fosebri.bfis.repository.impl;

import com.fosebri.bfis.entity.Client;
import com.fosebri.bfis.repository.ClientRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@Transactional
public class JpaClientRepository implements ClientRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Client save(Client client) {
        if (client.getId() == null) {
            entityManager.persist(client);
            return client;
        } else {
            return entityManager.merge(client);
        }
    }

    @Override
    public void changeStatus(UUID id, boolean status, Client client) {
        client.setEnabled(status);
        entityManager.merge(client);
    }

    @Override
    public Optional<Client> findById(UUID id) {
        return Optional.ofNullable(entityManager.find(Client.class, id));
    }

    @Override
    public List<Client> findClients() {
        return entityManager.createQuery("SELECT c FROM Client c", Client.class)
                .getResultList();
    }
}
