package com.fosebri.bfis.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Must not be blank") String username,
        @NotBlank(message = "Must not be blank") String password
) {
}
