package com.fosebri.bfis.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "operation")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "operation_type", length = 10)
public abstract class Operation {

    @Id
    @Column(name = "id", nullable = false)
    protected UUID id;

    @Column(name = "amount", nullable = false)
    protected double amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account", nullable = false)
    protected Account account;

    @Column(name = "performed_at", nullable = false)
    protected Instant performedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performed_by", nullable = false)
    protected User performedBy;
}
