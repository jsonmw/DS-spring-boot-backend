package com.debtsolver.DebtSolver.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CardDTO extends DebtDTO {

    private String cardType;
}
