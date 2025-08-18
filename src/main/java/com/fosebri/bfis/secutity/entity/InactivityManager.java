package com.fosebri.bfis.secutity.entity;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class InactivityManager {

    private final Map<String, Long> lastActivityMap = new ConcurrentHashMap<>();

    @Inject
    @ConfigProperty(name = "jwt.inactivity-limit")
    private long inactivityLimit;

    public void updateLastActivity(String token) {
        lastActivityMap.put(token, System.currentTimeMillis());
    }

    public boolean isSessionExpired(String token) {
        Long lastActivity = lastActivityMap.get(token);
        if (lastActivity == null) {
            return true;
        }
        return System.currentTimeMillis() - lastActivity > inactivityLimit;
    }

    public void removeToken(String token) {
        lastActivityMap.remove(token);
    }
}
