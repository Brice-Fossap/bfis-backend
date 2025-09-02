package com.fosebri.bfis.dto.client;

import com.fosebri.bfis.entity.Client;

import java.util.UUID;

public record UpdateClientResponse(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String address
) {
    public static UpdateClientResponse fromClient(Client client) {
        return new UpdateClientResponse(
                client.getId(),
                client.getPerson().getFirstName(),
                client.getPerson().getLastName(),
                client.getPerson().getEmail(),
                client.getPerson().getPhoneNumber(),
                client.getPerson().getAddress()
        );
    }
}
