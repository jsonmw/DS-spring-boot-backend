package com.debtsolver.DebtSolver.repository;

import com.debtsolver.DebtSolver.model.Debt;
import com.debtsolver.DebtSolver.model.PaymentPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentPlanRepository extends JpaRepository<PaymentPlan, Long> {

    // Enforces one active plan constraint on Debt models
    List<PaymentPlan> findByDebtAndIsActiveTrue(Debt debt);

}
