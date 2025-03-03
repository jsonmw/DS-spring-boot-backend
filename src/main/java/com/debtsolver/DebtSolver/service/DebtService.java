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
    DebtDTO getDebtById(Long id);

    /**
     * Retrieves all debts from database
     *
     * @return list of DTOs containing debt details for logged in user
     */
    List<DebtDTO> getAllDebts();

    /**
     * Creates new debt in the database
     *
     * @param debtRequest: a Request object representation of the debt details to be created
     * @return a DTO of the successfully created debt
     */
    DebtDTO createNewDebt(DebtRequest debtRequest);

    /**
     * Updates existing debt in the database
     *
     * @param updated: a DTO representation of the debt details to be updated
     * @param id : the id of the debt to be updated
     * @return a DTO of the successfully updated debt
     */
    DebtDTO updateDebtDetails(DebtDTO updated, Long id);

    /**
     * Deletes debt from the Database
     *
     * @param id: the numeric id of the debt object to be deleted
     * @return void
     */
    public void deleteDebtById(Long id);
}