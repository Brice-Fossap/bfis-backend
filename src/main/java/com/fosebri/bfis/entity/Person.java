package com.fosebri.bfis.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Table(name = "person")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Person extends AbstractEntity {

    @Id
    @Column(name = "id", nullable = false)
    protected UUID id;

    @Column(name = "first_name", nullable = false)
    protected String firstName;

    @Column(name = "last_name")
    protected String lastName;

    @Column(name = "email", nullable = false, unique = true)
    protected String email;

    @Column(name = "phone_number", nullable = false, unique = true)
    protected String phoneNumber;

    @Column(name = "address", nullable = false)
    protected String address;

    @OneToOne
    @JoinColumn(name = "user")
    protected User user;
}
