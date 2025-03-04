package com.debtsolver.DebtSolver.controller;

import com.debtsolver.DebtSolver.service.PaymentPlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PaymentPlanController {

    private final PaymentPlanService paymentPlanService;

}
