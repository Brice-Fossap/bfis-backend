package com.fosebri.bfis.dto.client;

import com.fosebri.bfis.entity.Client;

import java.util.List;
import java.util.UUID;

public record GetClientsResponse(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String address,
        boolean enabled
) {
    public static List<GetClientsResponse> fromClients(List<Client> clients) {
        return clients.stream()
                .map(client -> new GetClientsResponse(
                        client.getId(),
                        client.getPerson().getFirstName(),
                        client.getPerson().getLastName(),
                        client.getPerson().getEmail(),
                        client.getPerson().getPhoneNumber(),
                        client.getPerson().getAddress(),
                        client.isEnabled()))
                .toList();
    }
}
