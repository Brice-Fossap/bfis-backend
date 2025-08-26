package com.fosebri.bfis.dto.user;

import com.fosebri.bfis.entity.User;

import java.util.List;
import java.util.UUID;

public record GetUsersResponse(
        UUID id,
        String username,
        boolean enabled
) {
    public static List<GetUsersResponse> fromUsers(List<User> users) {
        return users.stream()
                .map(user -> new GetUsersResponse(
                        user.getId(),
                        user.getUsername(),
                        user.isEnabled()))
                .toList();
    }
}
