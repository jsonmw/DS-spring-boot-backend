package com.debtsolver.DebtSolver.controller;

import com.debtsolver.DebtSolver.dto.CardDTO;
import com.debtsolver.DebtSolver.dto.DebtDTO;
import com.debtsolver.DebtSolver.dto.LoanDTO;
import com.debtsolver.DebtSolver.io.DebtRequest;
import com.debtsolver.DebtSolver.io.DebtResponse;
import com.debtsolver.DebtSolver.model.User;
import com.debtsolver.DebtSolver.service.DebtService;
import com.debtsolver.DebtSolver.service.UserService;
import com.debtsolver.DebtSolver.util.Constants;
import com.debtsolver.DebtSolver.util.MappingUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DebtController {

    private final DebtService debtService;
    private final UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/")
    public List<DebtResponse> getDebts() {
        log.info("API GET /debts called");

        return new ArrayList<>();
    }


    /**
     * Creates new debt in database
     *
     * @param debtRequest
     * @return debtResponse
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/debts")
    public DebtResponse saveExpenseDetails(@RequestBody @Valid DebtRequest debtRequest) {
        log.info("API POST /debts called {} for OwnerID", debtRequest, debtRequest.getOwner());
        log.info("Received DebtRequest {}", debtRequest);

        DebtDTO debtDTO;
        User owner = MappingUtil.mapToNewClass(userService.getUserById(debtRequest.getOwner()), User.class); // extract owner for entity
        if (debtRequest.getDebtType().equals(Constants.CARD_TYPE)) {
            debtDTO = MappingUtil.mapToNewClass(debtRequest, CardDTO.class);
            log.info("New Debt of Loan Type: {}", debtDTO);
        } else if (debtRequest.getDebtType().equals(Constants.LOAN_TYPE)) {
            debtDTO = MappingUtil.mapToNewClass(debtRequest, LoanDTO.class);
            log.info("New Debt of Loan Type: {}", debtDTO);
        } else {
            return null;
        }

        debtDTO.setOwner(owner);
        debtDTO = debtService.createNewDebt(debtDTO);

        return MappingUtil.mapToNewClass(debtDTO, DebtResponse.class);
    }
}
