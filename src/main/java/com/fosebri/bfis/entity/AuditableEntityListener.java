package com.fosebri.bfis.entity;

import com.fosebri.bfis.repository.UserRepository;
import com.fosebri.bfis.service.UserService;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.Clock;
import java.time.Instant;

public class AuditableEntityListener {

    private static final Clock CLOCK = Clock.systemDefaultZone();

    @PrePersist
    public void prePersist(AuditableEntity entity) {
        var userService = CDI.current().select(UserService.class).get();
        var userRepository = CDI.current().select(UserRepository.class).get();

        var authenticatedUser = userService.getCurrentUser();
        authenticatedUser.ifPresent(user -> entity.setCreatedBy(userRepository.getReferenceById(user.getId())));
        entity.setCreatedAt(Instant.now(CLOCK));
    }

    @PreUpdate
    public void preUpdate(AuditableEntity entity) {
        var userService = CDI.current().select(UserService.class).get();
        var userRepository = CDI.current().select(UserRepository.class).get();

        var authenticatedUser = userService.getCurrentUser();
        authenticatedUser.ifPresent(user -> entity.setUpdatedBy(userRepository.getReferenceById(user.getId())));
        entity.setUpdatedAt(Instant.now(CLOCK));
    }
}
