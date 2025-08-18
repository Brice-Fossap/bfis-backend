package com.fosebri.bfis.secutity.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.PasswordHash;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class BCryptPasswordHash implements PasswordHash {

    @Inject
    @ConfigProperty(name = "password.hash.cost", defaultValue = "12")
    private int costFactor;

    @Override
    public String generate(char[] password) {
        return BCrypt.withDefaults().hashToString(costFactor, password);
    }

    @Override
    public boolean verify(char[] password, String hashedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(password, hashedPassword);
        return result.verified;
    }
}
