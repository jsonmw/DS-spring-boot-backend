package com.debtsolver.DebtSolver.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LoanDTO extends DebtDTO {

    private String LoanTerms;
}
