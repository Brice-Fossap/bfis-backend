package com.fosebri.bfis.service;

import com.fosebri.bfis.dto.auth.LoginRequest;
import com.fosebri.bfis.dto.auth.LoginResponse;
import com.fosebri.bfis.dto.auth.RefreshTokenRequest;
import com.fosebri.bfis.dto.auth.RefreshTokenResponse;
import com.fosebri.bfis.entity.User;
import com.fosebri.bfis.exception.InvalidCredentialsException;
import com.fosebri.bfis.exception.InvalidRefreshTokenException;
import com.fosebri.bfis.generator.IdGenerator;
import com.fosebri.bfis.repository.UserRepository;
import com.fosebri.bfis.secutity.JwtTokenUtil;
import com.fosebri.bfis.secutity.entity.InactivityManager;
import com.fosebri.bfis.secutity.service.UserIdentityStore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

@ApplicationScoped
public class AuthService {

    @Inject
    private UserIdentityStore identityStore;

    @Inject
    private JwtTokenUtil jwtTokenUtil;

    @Inject
    private UserRepository userRepository;

    @Inject
    private InactivityManager inactivityManager;

    @Inject
    private IdGenerator idGenerator;

    @Inject
    @ConfigProperty(name = "jwt.refresh-token-expiration")
    private long refreshTokenValidity;

    public LoginResponse login(LoginRequest request) {
        UsernamePasswordCredential credential =
                new UsernamePasswordCredential(request.username(), request.password());

        CredentialValidationResult result = identityStore.validate(credential);

        if (result.getStatus() != CredentialValidationResult.Status.VALID) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        var user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

        if (!user.isEnabled()) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        String accessToken = generateAccessToken(user);
        UUID refreshToken = idGenerator.next();

        user.setRefreshToken(refreshToken);
        user.setExpiry(Instant.now().plus(Duration.ofMillis(refreshTokenValidity)));
        userRepository.save(user);
        inactivityManager.updateLastActivity(accessToken);

        return new LoginResponse(accessToken, refreshToken);
    }

    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
        var user = userRepository.findByRefreshToken(UUID.fromString(request.refreshToken()))
                .orElseThrow(() -> new InvalidRefreshTokenException("Refresh token not found"));

        if (!user.isEnabled() || user.getRefreshToken() == null
                || !user.getRefreshToken().toString().equals(request.refreshToken())
                || user.getExpiry().isBefore(Instant.now())) {
            throw new InvalidRefreshTokenException("Invalid refresh token");
        }

        String newAccessToken = generateAccessToken(user);
        UUID refreshToken = idGenerator.next();

        user.setRefreshToken(refreshToken);
        user.setExpiry(Instant.now().plus(Duration.ofMillis(refreshTokenValidity)));
        userRepository.save(user);
        inactivityManager.updateLastActivity(newAccessToken);

        return new RefreshTokenResponse(newAccessToken, refreshToken);
    }

    private String generateAccessToken(User user) {
        return jwtTokenUtil.generateToken(user.getUsername(), Collections.singleton(user.getRole().name()));
    }
}
