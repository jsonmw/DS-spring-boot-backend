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
@DiscriminatorValue("LOAN")
public class Loan extends Debt {

    @NotBlank(message = "Loans need a valid term")
    private String terms;

}
