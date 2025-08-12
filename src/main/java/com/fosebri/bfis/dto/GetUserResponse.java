package com.fosebri.bfis.dto;

import com.fosebri.bfis.entity.User;

import java.util.UUID;

public record GetUserResponse(
        UUID id,
        String username,
        boolean enabled
) {
    public static GetUserResponse fromUser(User user) {
        return new GetUserResponse(
                user.getId(),
                user.getUsername(),
                user.isEnabled()
        );
    }
}
