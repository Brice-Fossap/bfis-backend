package com.fosebri.bfis.repository;

import com.fosebri.bfis.entity.Client;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientRepository {
    Client save(Client client);

    void changeStatus(UUID id, boolean status, Client client);

    Optional<Client> findById(UUID id);

    List<Client> findClients();
}
