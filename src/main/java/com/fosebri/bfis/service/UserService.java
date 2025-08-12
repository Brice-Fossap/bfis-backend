package com.fosebri.bfis.service;

import com.fosebri.bfis.dto.*;
import com.fosebri.bfis.entity.User;
import com.fosebri.bfis.exception.ChangePasswordException;
import com.fosebri.bfis.exception.ResourceAlreadyExistsException;
import com.fosebri.bfis.exception.ResourceNotFoundException;
import com.fosebri.bfis.generator.IdGenerator;
import com.fosebri.bfis.repository.UserRepository;
import com.fosebri.bfis.secutity.service.Argon2PasswordHasher;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserService {

    @Inject
    private UserRepository userRepository;

    @Inject
    private IdGenerator idGenerator;

    @Inject
    private Argon2PasswordHasher passwordHasher;

    public CreateUserResponse createUser(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new ResourceAlreadyExistsException("User already exists by username: " + request.username());
        }

        var user = request.toUser(idGenerator.next());
        user.setPassword(request.password(), passwordHasher);
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

    private User validateUser(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }
}
