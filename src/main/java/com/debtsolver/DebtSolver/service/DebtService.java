package com.debtsolver.DebtSolver.service;

import com.debtsolver.DebtSolver.dto.DebtDTO;

import java.util.List;

/**
 * Service interface for Expense module
 *
 * @author Jason
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
     * Retrieves all expenses from database
     *
     * @param ownerId: numeric id associated with the desired user
     * @return list of DTOs containing debt details
     */
    List<DebtDTO> getAllDebts(Long ownerId);
}
