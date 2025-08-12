package com.fosebri.bfis.entity;

import com.fosebri.bfis.secutity.service.Argon2PasswordHasher;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Table(name = "users")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User extends AbstractEntity {

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

    public void setPassword(String plainPassword, Argon2PasswordHasher hasher) {
        this.password = hasher.hash(plainPassword);
    }

    public boolean verifyPassword(String plainPassword, Argon2PasswordHasher hasher) {
        return hasher.verify(plainPassword, this.password);
    }
}
