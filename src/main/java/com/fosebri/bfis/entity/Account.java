package com.fosebri.bfis.entity;

import com.fosebri.bfis.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "account")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "account_type", discriminatorType = DiscriminatorType.STRING, length = 8)
public abstract class Account extends AuditableEntity {

    @Id
    @Column(name = "account_number", nullable = false)
    protected String accountNumber;

    @Builder.Default
    @Column(name = "balance", nullable = false)
    protected double balance = 0.0;

    @Column(name = "status", nullable = false)
    protected AccountStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client", nullable = false)
    protected Client client;
}
