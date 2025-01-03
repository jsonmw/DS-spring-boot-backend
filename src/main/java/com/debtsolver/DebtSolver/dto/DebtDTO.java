package com.debtsolver.DebtSolver.dto;

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

    Long id;
    String name;
    String debtType;
    BigDecimal apr;
    BigDecimal balance;
    String description;
    Timestamp createdA;
    Timestamp updatedAt;
    Long ownerId;
}
