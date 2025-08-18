package com.fosebri.bfis.secutity.service;

import com.fosebri.bfis.exception.InvalidTokenException;
import com.fosebri.bfis.secutity.JwtTokenUtil;
import com.fosebri.bfis.secutity.entity.InactivityManager;
import com.fosebri.bfis.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.CallerPrincipal;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class JWTAuthenticationMechanism implements HttpAuthenticationMechanism {

    @Inject
    private JwtTokenUtil jwtTokenUtil;

    @Inject
    private UserService userService;

    @Inject
    private InactivityManager inactivityManager;

    private static final List<String> EXCLUDED_PATHS = Arrays.asList(
            "/auth/login",
            "/auth/refresh-token"
    );

    @Override
    public AuthenticationStatus validateRequest(
            HttpServletRequest request,
            HttpServletResponse response,
            HttpMessageContext httpMessageContext
    ) throws AuthenticationException {

        String path = request.getRequestURI();
        String contextPath = request.getContextPath();
        String relativePath = path.substring(contextPath.length());

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return httpMessageContext.doNothing();
        }

        if (EXCLUDED_PATHS.stream().anyMatch(relativePath::startsWith)) {
            return httpMessageContext.doNothing();
        }

        String token = extractJwtToken(request);
        if (token == null) {
            return httpMessageContext.responseUnauthorized();
        }

        try {
            String username = jwtTokenUtil.getUsernameFromToken(token);
            if (!jwtTokenUtil.isValidToken(token, username)) {
                throw new InvalidTokenException("Invalid or expired token");
            }

            if (inactivityManager.isSessionExpired(token)) {
                inactivityManager.removeToken(token);
                throw new InvalidTokenException("Session expired due to inactivity");
            }

            var user = userService.findByUsername(username);
            if (user == null || !user.isEnabled()) {
                throw new InvalidTokenException("User not found");
            }

            inactivityManager.updateLastActivity(token);

            Set<String> roles = Set.of(user.getRole().name());
            CallerPrincipal principal = new CallerPrincipal(username);

            return httpMessageContext.notifyContainerAboutLogin(principal, roles);

        } catch (InvalidTokenException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidTokenException("Authentication failed: " + e.getMessage());
        }
    }

    private String extractJwtToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}