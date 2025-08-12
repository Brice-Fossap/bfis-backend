package com.fosebri.bfis.secutity.service;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Argon2PasswordHasher {

    private static final int ITERATIONS = 10;
    private static final int MEMORY = 65536;
    private static final int PARALLELISM = 2;
    private static final int SALT_LENGTH = 16;
    private static final int HASH_LENGTH = 32;

    private final Argon2 argon2 = Argon2Factory.create(
            Argon2Factory.Argon2Types.ARGON2id,
            SALT_LENGTH,
            HASH_LENGTH
    );

    public String hash(String plainPassword) {
        try {
            return argon2.hash(
                    ITERATIONS,
                    MEMORY,
                    PARALLELISM,
                    plainPassword.toCharArray()
            );
        } finally {
            argon2.wipeArray(plainPassword.toCharArray());
        }
    }

    public boolean verify(String plainPassword, String hashedPassword) {
        try {
            return argon2.verify(hashedPassword, plainPassword.toCharArray());
        } finally {
            argon2.wipeArray(plainPassword.toCharArray());
        }
    }
}
