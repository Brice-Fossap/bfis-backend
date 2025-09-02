package com.fosebri.bfis.repository;

import com.fosebri.bfis.entity.Employee;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository {
    Employee save(Employee employee);

    void changeStatus(UUID id, boolean status, Employee employee);

    Optional<Employee> findById(UUID id);

    List<Employee> findEmployees();
}
