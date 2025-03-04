package com.debtsolver.DebtSolver.service.impl;

import com.debtsolver.DebtSolver.model.PaymentPlan;
import com.debtsolver.DebtSolver.repository.PaymentPlanRepository;
import com.debtsolver.DebtSolver.service.PaymentPlanService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentPlanServiceImpl implements PaymentPlanService {

    private final PaymentPlanRepository paymentPlanRepository;


    /**
     * Enforces constraint that only a single plan is active
     *
     * @param newPlan
     * @return void
     */
    @Transactional
    public void setActivePlan(PaymentPlan newPlan) {
        List<PaymentPlan> existingPlans = paymentPlanRepository.findByDebtAndIsActiveTrue(newPlan.getDebt());

        // deactivate all existing plans
        for (PaymentPlan plan : existingPlans) {
            plan.setIsActive(false);
            paymentPlanRepository.save(plan);
        }

        // activate new plan
        newPlan.setIsActive(true);
        paymentPlanRepository.save(newPlan);
    }
}
