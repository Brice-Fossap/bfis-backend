package com.fosebri.bfis.repository;

import com.fosebri.bfis.entity.Person;

public interface PersonRepository {
    void save(Person person);

    boolean existsByEmail(String email);
}
