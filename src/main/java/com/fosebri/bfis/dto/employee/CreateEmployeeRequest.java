package com.fosebri.bfis.dto.employee;

import com.fosebri.bfis.entity.Employee;
import com.fosebri.bfis.entity.Person;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record CreateEmployeeRequest(
        @NotBlank(message = "Must not be blank") String firstName,
        String lastName,
        @NotBlank(message = "Must not be blank") String email,
        String phoneNumber,
        String address,
        String position
) {
    public Employee toEmployee(UUID id, Person person) {
        return Employee.builder()
                .id(id)
                .person(person)
                .position(position)
                .build();
    }

    public Person toPerson(UUID id) {
        return Person.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phoneNumber(phoneNumber)
                .address(address)
                .build();
    }
}
