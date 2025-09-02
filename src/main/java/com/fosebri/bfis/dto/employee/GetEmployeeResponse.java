package com.fosebri.bfis.dto.employee;

import com.fosebri.bfis.entity.Employee;

import java.util.UUID;

public record GetEmployeeResponse(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String address,
        boolean enabled,
        String position
) {
    public static GetEmployeeResponse fromEmployee(Employee employee) {
        var person = employee.getPerson();
        return new GetEmployeeResponse(
                employee.getId(),
                person.getFirstName(),
                person.getLastName(),
                person.getEmail(),
                person.getPhoneNumber(),
                person.getAddress(),
                employee.isEnabled(),
                employee.getPosition()
        );
    }
}
