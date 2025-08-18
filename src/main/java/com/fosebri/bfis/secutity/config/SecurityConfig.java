package com.fosebri.bfis.secutity.config;

import jakarta.annotation.security.DeclareRoles;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@DeclareRoles({"SUPER_ADMIN", "ADMIN", "USER"})
public class SecurityConfig {
}
