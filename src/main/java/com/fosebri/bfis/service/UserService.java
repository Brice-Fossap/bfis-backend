package com.fosebri.bfis.service;

import com.fosebri.bfis.dto.user.*;
import com.fosebri.bfis.entity.User;
import com.fosebri.bfis.exception.ChangePasswordException;
import com.fosebri.bfis.exception.ResourceAlreadyExistsException;
import com.fosebri.bfis.exception.ResourceNotFoundException;
import com.fosebri.bfis.generator.IdGenerator;
import com.fosebri.bfis.repository.UserRepository;
import com.fosebri.bfis.secutity.service.BCryptPasswordHash;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class UserService {

    @Inject
    private UserRepository userRepository;

    @Inject
    private IdGenerator idGenerator;

    @Inject
    private BCryptPasswordHash passwordHash;

    @Context
    private SecurityContext securityContext;

    public CreateUserResponse createUser(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new ResourceAlreadyExistsException("User already exists by username: " + request.username());
        }

        var user = request.toUser(idGenerator.next());
        user.setPassword(request.password(), passwordHash);
        return CreateUserResponse.fromUser(userRepository.save(user));
    }

    public void changeUserPassword(UUID userId, ChangePasswordRequest request) {
        var user = validateUser(userId);

        if (user.getPassword().equals(request.password())) {
            throw new ChangePasswordException("New password cannot be the same as the old password.");
        }

        userRepository.changePassword(userId, request.password(), user);
    }

    public void changeUserStatus(UUID userId, ChangeUserStatusRequest request) {
        var user = validateUser(userId);

        userRepository.changeStatus(userId, request.enabled(), user);
    }

    public GetUserResponse getUser(UUID userId) {
        var user = validateUser(userId);

        return GetUserResponse.fromUser(user);
    }

    public List<GetUsersResponse> getUsers() {
        var users = userRepository.findUsers();
        return GetUsersResponse.fromUsers(users);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
    }

    public Optional<User> getCurrentUserThrows() {
        if (securityContext.getUserPrincipal() == null) {
            throw new IllegalStateException("No users connected");
        }

        String username = securityContext.getUserPrincipal().getName();
        return userRepository.findByUsername(username);
    }

    public Optional<User> getCurrentUser() {
        if (securityContext.getUserPrincipal() == null) {
            return Optional.empty();
        }

        String username = securityContext.getUserPrincipal().getName();
        return userRepository.findByUsername(username);
    }

    private User validateUser(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }
}
