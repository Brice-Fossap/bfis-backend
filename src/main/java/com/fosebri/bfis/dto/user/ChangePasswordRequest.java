package com.fosebri.bfis.dto.user;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordRequest(@NotBlank(message = "Must not be blank") String password) {
}
