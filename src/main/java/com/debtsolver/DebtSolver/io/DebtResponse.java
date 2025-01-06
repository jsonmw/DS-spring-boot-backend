package com.debtsolver.DebtSolver.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DebtResponse {

    private Long id;
    private String name;
    private String debtType;
    private String cardType;
    private String terms;
    private BigDecimal apr;
    private BigDecimal balance;
    private String description;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
