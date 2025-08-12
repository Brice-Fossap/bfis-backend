package com.fosebri.bfis.entity;

import com.fosebri.bfis.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public abstract class Account extends AbstractEntity {

    @Id
    @Column(name = "account_number", nullable = false)
    protected String accountNumber;

    @Column(name = "balance", nullable = false)
    protected double balance;

    @Column(name = "status", nullable = false)
    protected AccountStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client", nullable = false)
    protected Client client;
}
