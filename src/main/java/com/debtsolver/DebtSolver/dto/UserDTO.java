package com.debtsolver.DebtSolver.dto;

import com.debtsolver.DebtSolver.model.Debt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String bio;
    private String password;
    private List<Debt> debts;
}
