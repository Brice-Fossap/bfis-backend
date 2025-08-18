package com.fosebri.bfis.dto.user;

import com.fosebri.bfis.entity.User;
import com.fosebri.bfis.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateUserRequest(
        @NotBlank(message = "Must not be blank") String username,
        @NotBlank(message = "Must not be blank") String password,
        @NotNull(message = "Must not be null") UserRole role
) {
    public User toUser(UUID id) {
        return User.builder()
                .id(id)
                .username(username)
                .role(role)
                .build();
    }
}
