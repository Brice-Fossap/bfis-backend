package com.fosebri.bfis.dto.auth;

import java.util.UUID;

public record LoginResponse(
    String accessToken,
    UUID refreshToken
) {
}
