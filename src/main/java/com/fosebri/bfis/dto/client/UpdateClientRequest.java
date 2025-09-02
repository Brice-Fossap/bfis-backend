package com.fosebri.bfis.dto.client;

import jakarta.validation.constraints.NotBlank;

public record UpdateClientRequest(
        @NotBlank(message = "Must not be blank") String firstName,
        String lastName,
        @NotBlank(message = "Must not be blank") String email,
        String phoneNumber,
        String address
) {
}
