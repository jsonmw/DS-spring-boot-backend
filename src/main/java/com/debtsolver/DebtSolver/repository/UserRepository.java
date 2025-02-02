package com.debtsolver.DebtSolver.repository;

import com.debtsolver.DebtSolver.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserAccount, Long> {

    /**
     * Fetches a user associated with a specific email.
     *
     * @param email:      the email String of the user being fetched
     * @return an Optional that may contain the user if found, or empty Optional if not
     */
    Optional<UserAccount> findByEmail(String email);

    Boolean existsByEmail(String email);

}
