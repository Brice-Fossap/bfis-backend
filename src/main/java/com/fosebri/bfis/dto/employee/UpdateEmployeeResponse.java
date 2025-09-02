package com.fosebri.bfis.dto.employee;

import com.fosebri.bfis.entity.Employee;

import java.util.UUID;

public record UpdateEmployeeResponse(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String address,
        String position
) {
    public static UpdateEmployeeResponse fromEmployee(Employee employee) {
        return new UpdateEmployeeResponse(
                employee.getId(),
                employee.getPerson().getFirstName(),
                employee.getPerson().getLastName(),
                employee.getPerson().getEmail(),
                employee.getPerson().getPhoneNumber(),
                employee.getPerson().getAddress(),
                employee.getPosition()
        );
    }
}
