package com.fosebri.bfis.repository;

import com.fosebri.bfis.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User save(User user);

    void changePassword(UUID id, String newPassword, User user);

    void changeStatus(UUID id, boolean status, User user);

    Optional<User> findByUsername(String username);

    Optional<User> findById(UUID id);

    Optional<User> findByRefreshToken(UUID refreshToken);

    List<User> findUsers();

    boolean existsByUsername(String username);

    User getReferenceById(UUID id);
}
