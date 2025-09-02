package com.fosebri.bfis.dto.employee;

import jakarta.validation.constraints.NotBlank;

public record UpdateEmployeeRequest(
        @NotBlank(message = "Must not be blank") String firstName,
        String lastName,
        @NotBlank(message = "Must not be blank") String email,
        String phoneNumber,
        String address,
        String position
) {
}
