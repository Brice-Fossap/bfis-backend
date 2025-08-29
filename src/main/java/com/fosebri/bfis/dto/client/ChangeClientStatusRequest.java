package com.fosebri.bfis.dto.client;

import jakarta.validation.constraints.NotNull;

public record ChangeClientStatusRequest(@NotNull(message = "Must not be null") Boolean enabled) {
}
