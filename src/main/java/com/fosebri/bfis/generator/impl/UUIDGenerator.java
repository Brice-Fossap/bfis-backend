package com.fosebri.bfis.generator.impl;

import com.fosebri.bfis.generator.IdGenerator;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class UUIDGenerator implements IdGenerator {

    @Override
    public UUID next() {
        return UUID.randomUUID();
    }
}
