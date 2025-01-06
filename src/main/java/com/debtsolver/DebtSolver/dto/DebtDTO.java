package com.debtsolver.DebtSolver.dto;

import com.debtsolver.DebtSolver.model.User;
import com.debtsolver.DebtSolver.util.DebtType;
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
public class DebtDTO {

    private Long id;
    private String name;
    private DebtType debtType;
    private BigDecimal apr;
    private BigDecimal balance;
    private String description;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private User owner;
}
