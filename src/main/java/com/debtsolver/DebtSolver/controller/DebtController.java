package com.debtsolver.DebtSolver.controller;

import com.debtsolver.DebtSolver.dto.DebtDTO;
import com.debtsolver.DebtSolver.exception.InvalidDebtTypeException;
import com.debtsolver.DebtSolver.io.DebtRequest;
import com.debtsolver.DebtSolver.io.DebtResponse;
import com.debtsolver.DebtSolver.service.DebtService;
import com.debtsolver.DebtSolver.service.UserService;
import com.debtsolver.DebtSolver.util.DebtType;
import com.debtsolver.DebtSolver.util.MappingUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @PostMapping("/debts")
    public ResponseEntity<DebtResponse> saveExpenseDetails(@RequestBody @Valid DebtRequest debtRequest) {
        log.info("API POST /debts called for OwnerID: {}", debtRequest.getOwner());

        if (!DebtType.isValid(debtRequest.getDebtType())) {
            throw new InvalidDebtTypeException("Invalid Debt Type: " + debtRequest.getDebtType());
        }

        DebtDTO debtDTO = debtService.createNewDebt(debtRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(MappingUtil.mapToNewClass(debtDTO, DebtResponse.class));
    }
}
