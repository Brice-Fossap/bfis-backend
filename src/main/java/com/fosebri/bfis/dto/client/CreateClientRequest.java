package com.fosebri.bfis.dto.client;

import com.fosebri.bfis.entity.Client;
import com.fosebri.bfis.entity.Person;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record CreateClientRequest(
        @NotBlank(message = "Must not be blank") String firstName,
        String lastName,
        @NotBlank(message = "Must not be blank") String email,
        String phoneNumber,
        String address
) {
    public Client toClient(UUID id, Person person) {
        return Client.builder()
                .id(id)
                .person(person)
                .build();
    }

    public Person toPerson(UUID id) {
        return Person.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phoneNumber(phoneNumber)
                .address(address)
                .build();
    }
}
