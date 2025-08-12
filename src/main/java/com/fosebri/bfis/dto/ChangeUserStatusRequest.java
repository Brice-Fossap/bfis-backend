package com.fosebri.bfis.dto;

import jakarta.validation.constraints.NotNull;

public record ChangeUserStatusRequest(@NotNull Boolean enabled) {
}
