package com.fosebri.bfis.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DiscriminatorValue("SAVING")
public class SavingAccount extends Account {

    @Column(name = "interest_rate", nullable = false)
    private double interestRate;
}
