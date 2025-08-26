package com.fosebri.bfis.dto.user;

import jakarta.validation.constraints.NotNull;

public record ChangeUserStatusRequest(@NotNull(message = "Must not be null") Boolean enabled) {
}
