package com.fosebri.bfis.dto.employee;

import jakarta.validation.constraints.NotNull;

public record ChangeEmployeeStatusRequest(@NotNull(message = "Must not be null") Boolean enabled) {
}
