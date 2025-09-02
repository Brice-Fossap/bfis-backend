package com.fosebri.bfis.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "client")
public class Client {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Builder.Default
    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    @OneToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;
}
