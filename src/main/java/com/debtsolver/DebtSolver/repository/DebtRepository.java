package com.debtsolver.DebtSolver.repository;

import com.debtsolver.DebtSolver.model.Debt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for Debt resources
 *
 * @author Jason Wild
 */
@Repository
public interface DebtRepository extends JpaRepository<Debt, Long> {

    /**
     * Fetches a single debt associated with a specific user id.
     *
     * @param id:      the id of the debt being fetched
     * @param ownerId: the id of the user whose debts are being fetched
     * @return an Optional that may contain the debt if found, or empty Optional if not
     */
    Optional<Debt> findByIdAndOwnerId(Long id, Long ownerId);

    /**
     * Fetches all debts associated with a specific user.
     *
     * @param ownerId: the id of the user whose debts are being fetched
     * @return a list of debts belonging to the specified user
     */
    List<Debt> findByOwnerId(Long ownerId);

    /**
     * Fetches all debts associated with a specific type.
     *
     * @param type:    Debt type to be fetched
     * @param ownerId: the id of the user whose debts are being fetched
     * @return a list of debts belonging to the specified user
     */
    List<Debt> findByDebtTypeAndOwnerId(String debtType, Long ownerId);


}
