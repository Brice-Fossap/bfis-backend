package com.fosebri.bfis.service;

import com.fosebri.bfis.dto.employee.*;
import com.fosebri.bfis.entity.Employee;
import com.fosebri.bfis.exception.ResourceAlreadyExistsException;
import com.fosebri.bfis.exception.ResourceNotFoundException;
import com.fosebri.bfis.generator.IdGenerator;
import com.fosebri.bfis.repository.EmployeeRepository;
import com.fosebri.bfis.repository.PersonRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class EmployeeService {

    @Inject
    private EmployeeRepository employeeRepository;

    @Inject
    private PersonRepository personRepository;

    @Inject
    private IdGenerator idGenerator;

    public CreateEmployeeResponse createEmployee(CreateEmployeeRequest request) {
        if (personRepository.existsByEmail(request.email())) {
            throw new ResourceAlreadyExistsException("Employee already exists by email: " + request.email());
        }

        UUID personId = idGenerator.next();
        var person = request.toPerson(personId);
        personRepository.save(person);

        var employee = request.toEmployee(personId, person);
        return CreateEmployeeResponse.fromEmployee(employeeRepository.save(employee));
    }

    public UpdateEmployeeResponse updateEmployee(UUID id, UpdateEmployeeRequest request) {
        var employee = validateEmployee(id);

        if (!employee.getPerson().getEmail().equals(request.email())
                && personRepository.existsByEmail(request.email())) {
            throw new ResourceAlreadyExistsException("Employee already exists by email: " + request.email());
        }

        updateEmployee(employee, request);

        return UpdateEmployeeResponse.fromEmployee(employeeRepository.save(employee));
    }

    public void changeStatus(UUID id, ChangeEmployeeStatusRequest request) {
        var employee = validateEmployee(id);

        employeeRepository.changeStatus(id, request.enabled(), employee);
    }

    public GetEmployeeResponse getEmployee(UUID id) {
        var employee = validateEmployee(id);

        return GetEmployeeResponse.fromEmployee(employee);
    }

    public List<GetEmployeesResponse> getEmployees() {
        var employees = employeeRepository.findEmployees();

        return GetEmployeesResponse.fromEmployees(employees);
    }

    private Employee validateEmployee(UUID id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    private void updateEmployee(Employee employee, UpdateEmployeeRequest request) {
        employee.getPerson().setFirstName(request.firstName());
        employee.getPerson().setLastName(request.lastName());
        employee.getPerson().setEmail(request.email());
        employee.getPerson().setPhoneNumber(request.phoneNumber());
        employee.getPerson().setAddress(request.address());
        employee.setPosition(request.position());
    }
}
