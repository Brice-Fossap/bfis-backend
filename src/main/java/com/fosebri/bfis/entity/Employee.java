package com.fosebri.bfis.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "employee")
public class Employee {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "position", nullable = false)
    private String position;

    @Builder.Default
    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    @OneToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;
}
