package com.fosebri.bfis.entity;

import jakarta.persistence.*;
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
@DiscriminatorValue("TRANSFER")
public class Transfer extends Operation {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_account", nullable = false)
    private Account destinationAccount;
}
