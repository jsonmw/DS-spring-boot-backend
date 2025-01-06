package com.debtsolver.DebtSolver.service;

import com.debtsolver.DebtSolver.dto.DebtDTO;
import com.debtsolver.DebtSolver.io.DebtRequest;

import java.util.List;

/**
 * Service interface for Debt objects
 *
 * @author Jason Wild
 */
public interface DebtService {

    /**
     * Retrieves a single debt by ID
     *
     * @param id:      numeric associated with the given debt
     * @param ownerId: numeric associated the owner of the debt
     * @return DTO containing the debt details
     */
    DebtDTO getDebtByDebtId(Long id, Long ownerId);

    /**
     * Retrieves all debts from database
     *
     * @param ownerId: numeric id associated with the desired user
     * @return list of DTOs containing debt details
     */
    List<DebtDTO> getAllDebts(Long ownerId);

    /**
     * Creates new debt in the database
     *
     * @param debtRequest: a Request object representation of the debt details to be created
     * @return a DTO of the successfully created debt
     */
    public DebtDTO createNewDebt(DebtRequest debtRequest);
}
