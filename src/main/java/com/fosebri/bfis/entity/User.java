package com.fosebri.bfis.entity;

import com.fosebri.bfis.enums.UserRole;
import com.fosebri.bfis.secutity.service.BCryptPasswordHash;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User extends AuditableEntity {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Builder.Default
    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    @Column(name = "refresh_token")
    private UUID refreshToken;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expiry")
    private Instant expiry;

    public void setPassword(String plainPassword, BCryptPasswordHash hasher) {
        this.password = hasher.generate(plainPassword.toCharArray());
    }

    public boolean verifyPassword(String plainPassword, BCryptPasswordHash hasher) {
        return hasher.verify(plainPassword.toCharArray(), this.password);
    }
}
