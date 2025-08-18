package com.fosebri.bfis.dto.auth;

import java.util.UUID;

public record RefreshTokenResponse(
        String accessToken,
        UUID refreshToken
) {
}
