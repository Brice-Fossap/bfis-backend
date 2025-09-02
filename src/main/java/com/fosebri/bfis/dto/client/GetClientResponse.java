package com.fosebri.bfis.dto.client;

import com.fosebri.bfis.entity.Client;

import java.util.UUID;

public record GetClientResponse(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String address,
        boolean enabled
) {
    public static GetClientResponse fromClient(Client client) {
        var person = client.getPerson();
        return new GetClientResponse(
                client.getId(),
                person.getFirstName(),
                person.getLastName(),
                person.getEmail(),
                person.getPhoneNumber(),
                person.getAddress(),
                client.isEnabled()
        );
    }
}
