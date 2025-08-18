package com.fosebri.bfis.secutity.service;

import com.fosebri.bfis.service.UserService;
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
    private UserService userService;

    @Inject
    private BCryptPasswordHash passwordHash;

    @Override
    public CredentialValidationResult validate(Credential credential) {
        if (credential instanceof UsernamePasswordCredential usernamePassword) {
            String username = usernamePassword.getCaller();
            String password = usernamePassword.getPasswordAsString();

            var user = userService.findByUsername(username);

            if (user == null || !user.isEnabled()) {
                return CredentialValidationResult.INVALID_RESULT;
            }

            if (user.verifyPassword(password, passwordHash)) {
                Set<String> roles = Set.of(user.getRole().name());
                return new CredentialValidationResult(username, roles);
            }
        }

        return CredentialValidationResult.INVALID_RESULT;
    }

    @Override
    public Set<ValidationType> validationTypes() {
        return Set.of(ValidationType.VALIDATE);
    }
}
