package com.debtsolver.DebtSolver.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("Card")
public class Card extends Debt {

    @NotBlank(message = "Cards need a valid Card Type")
    private String cardType;

}
