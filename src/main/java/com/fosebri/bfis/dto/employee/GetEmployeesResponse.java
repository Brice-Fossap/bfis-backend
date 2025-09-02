package com.fosebri.bfis.dto.employee;

import com.fosebri.bfis.entity.Employee;

import java.util.List;
import java.util.UUID;

public record GetEmployeesResponse(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String address,
        boolean enabled,
        String position
) {
    public static List<GetEmployeesResponse> fromEmployees(List<Employee> employees) {
        return employees.stream()
                .map(employee -> new GetEmployeesResponse(
                        employee.getId(),
                        employee.getPerson().getFirstName(),
                        employee.getPerson().getLastName(),
                        employee.getPerson().getEmail(),
                        employee.getPerson().getPhoneNumber(),
                        employee.getPerson().getAddress(),
                        employee.isEnabled(),
                        employee.getPosition()))
                .toList();
    }
}
