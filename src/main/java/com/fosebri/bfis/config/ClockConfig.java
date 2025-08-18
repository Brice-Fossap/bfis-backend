package com.fosebri.bfis.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import java.time.Clock;

@ApplicationScoped
public class ClockConfig {

    @Produces
    @ApplicationScoped
    public Clock produceClock() {
        return Clock.systemDefaultZone();
    }
}
