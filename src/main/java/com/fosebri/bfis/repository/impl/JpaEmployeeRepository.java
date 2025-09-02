package com.fosebri.bfis.repository.impl;

import com.fosebri.bfis.entity.Employee;
import com.fosebri.bfis.repository.EmployeeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@Transactional
public class JpaEmployeeRepository implements EmployeeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Employee save(Employee employee) {
        if (employee.getId() == null) {
            entityManager.persist(employee);
            return employee;
        } else {
            return entityManager.merge(employee);
        }
    }

    @Override
    public void changeStatus(UUID id, boolean status, Employee employee) {
        employee.setEnabled(status);
        entityManager.merge(employee);
    }

    @Override
    public Optional<Employee> findById(UUID id) {
        return Optional.ofNullable(entityManager.find(Employee.class, id));
    }

    @Override
    public List<Employee> findEmployees() {
        return entityManager.createQuery("SELECT e FROM Employee e", Employee.class)
                .getResultList();
    }
}
