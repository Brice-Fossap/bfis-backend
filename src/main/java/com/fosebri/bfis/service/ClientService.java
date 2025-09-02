package com.fosebri.bfis.service;

import com.fosebri.bfis.dto.client.*;
import com.fosebri.bfis.entity.Client;
import com.fosebri.bfis.exception.ResourceAlreadyExistsException;
import com.fosebri.bfis.exception.ResourceNotFoundException;
import com.fosebri.bfis.generator.IdGenerator;
import com.fosebri.bfis.repository.ClientRepository;
import com.fosebri.bfis.repository.PersonRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ClientService {

    @Inject
    private ClientRepository clientRepository;

    @Inject
    private PersonRepository personRepository;

    @Inject
    private IdGenerator idGenerator;

    @Transactional
    public CreateClientResponse createClient(CreateClientRequest request) {
        if (personRepository.existsByEmail(request.email())) {
            throw new ResourceAlreadyExistsException("Client already exists by email: " + request.email());
        }

        UUID personId = idGenerator.next();
        var person = request.toPerson(personId);
        personRepository.save(person);

        var client = request.toClient(personId, person);
        return CreateClientResponse.fromClient(clientRepository.save(client));
    }

    public UpdateClientResponse updateClient(UUID clientId, UpdateClientRequest request) {
        var client = validateClient(clientId);

        if (!client.getPerson().getEmail().equals(request.email()) && personRepository.existsByEmail(request.email())) {
            throw new ResourceAlreadyExistsException("Client already exists by email: " + request.email());
        }

        updateClient(client, request);

        return UpdateClientResponse.fromClient(clientRepository.save(client));
    }

    public void changeStatus(UUID clientId, ChangeClientStatusRequest request) {
        var client = validateClient(clientId);

        clientRepository.changeStatus(clientId, request.enabled(), client);
    }

    public GetClientResponse getClient(UUID clientId) {
        var client = validateClient(clientId);

        return GetClientResponse.fromClient(client);
    }

    public List<GetClientsResponse> getClients() {
        var clients = clientRepository.findClients();
        return GetClientsResponse.fromClients(clients);
    }

    private Client validateClient(UUID clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + clientId));
    }

    private void updateClient(Client client, UpdateClientRequest request) {
        client.getPerson().setFirstName(request.firstName());
        client.getPerson().setLastName(request.lastName());
        client.getPerson().setEmail(request.email());
        client.getPerson().setPhoneNumber(request.phoneNumber());
        client.getPerson().setAddress(request.address());
    }
}
