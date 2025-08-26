package com.fosebri.bfis.secutity.service;

import com.fosebri.bfis.exception.InvalidCredentialsException;
import com.fosebri.bfis.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;

import java.util.Set;

@ApplicationScoped
public class UserIdentityStore implements IdentityStore {

    @Inject
    private UserRepository userRepository;

    @Inject
    private BCryptPasswordHash passwordHash;

    @Override
    public CredentialValidationResult validate(Credential credential) {
        if (credential instanceof UsernamePasswordCredential usernamePassword) {
            String username = usernamePassword.getCaller();
            String password = usernamePassword.getPasswordAsString();

            var user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

            if (user == null || !user.isEnabled()) {
                throw new InvalidCredentialsException("Invalid username or password");
            }

            if (user.verifyPassword(password, passwordHash)) {
                Set<String> roles = Set.of(user.getRole().name());
                return new CredentialValidationResult(username, roles);
            } else {
                throw new InvalidCredentialsException("Invalid username or password");
            }
        }

        throw new InvalidCredentialsException("Invalid username or password");
    }

    @Override
    public Set<ValidationType> validationTypes() {
        return Set.of(ValidationType.VALIDATE);
    }
}
