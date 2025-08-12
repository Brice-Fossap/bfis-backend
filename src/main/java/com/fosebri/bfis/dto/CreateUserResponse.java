package com.fosebri.bfis.dto;

import com.fosebri.bfis.entity.User;

import java.util.UUID;

public record CreateUserResponse(
        UUID id,
        String username,
        boolean enabled
) {
    public static CreateUserResponse fromUser(User user) {
        return new CreateUserResponse(user.getId(), user.getUsername(), user.isEnabled());
    }
}
