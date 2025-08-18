package com.fosebri.bfis.dto.user;

import com.fosebri.bfis.entity.User;
import com.fosebri.bfis.enums.UserRole;

import java.util.UUID;

public record CreateUserResponse(
        UUID id,
        String username,
        boolean enabled,
        UserRole role
) {
    public static CreateUserResponse fromUser(User user) {
        return new CreateUserResponse(user.getId(), user.getUsername(), user.isEnabled(), user.getRole());
    }
}
