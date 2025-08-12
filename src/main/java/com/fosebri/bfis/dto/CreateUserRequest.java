package com.fosebri.bfis.dto;

import com.fosebri.bfis.entity.User;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record CreateUserRequest(
        @NotBlank String username,
        @NotBlank String password
) {
    public User toUser(UUID id) {
        return User.builder()
                .id(id)
                .username(username)
                .build();
    }
}
