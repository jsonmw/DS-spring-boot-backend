package com.debtsolver.DebtSolver.io;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DebtRequest {

    private Long ownerId; // TODO: will remove this once I implement the auth

    @NotBlank(message = "Debt name is required")
    @Size(min = 3, message = "Debt name is must be at least 3 characters.")
    private String name;

    @NotBlank(message = "Debt type is required")
    private String debtType;

    @NotBlank(message = "APR is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "APR must be positive")
    private BigDecimal apr;

    @NotBlank(message = "Balance is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Balance must be positive")
    private BigDecimal balance;

    private String description;

}
