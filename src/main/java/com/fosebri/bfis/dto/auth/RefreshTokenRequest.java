package com.fosebri.bfis.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
        @NotBlank(message = "Must not be blank") String refreshToken
) {
}
