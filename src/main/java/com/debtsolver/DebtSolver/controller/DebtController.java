package com.debtsolver.DebtSolver.controller;

import com.debtsolver.DebtSolver.dto.DebtDTO;
import com.debtsolver.DebtSolver.exception.InvalidDebtTypeException;
import com.debtsolver.DebtSolver.io.DebtRequest;
import com.debtsolver.DebtSolver.io.DebtResponse;
import com.debtsolver.DebtSolver.service.DebtService;
import com.debtsolver.DebtSolver.service.UserService;
import com.debtsolver.DebtSolver.util.DebtType;
import com.debtsolver.DebtSolver.util.MappingUtil;
import com.debtsolver.DebtSolver.util.Routes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DebtController {

    private final DebtService debtService;
    private final UserService userService;

    /**
     * Returns a list of all debts associated with the user
     *
     * @param userId: denotes the user to find debts for (TODO: REMOVE THIS WHEN AUTH IMPLEMENTED)
     * @return Response Entity containing a list of Debts for the give user
     */
    @GetMapping(Routes.ALL_DEBTS + "{userId}")
    public ResponseEntity<List<DebtResponse>> getDebts(@PathVariable Long userId) {
        // TODO: replace PathVariable when authentication implemented, and get from token
        log.info("API GET /debts called");

        List<DebtDTO> debts = debtService.getAllDebts(userId);
        log.info("Found {} debts for {}", debts.size(), userService.getUserById(userId).getName());
        List<DebtResponse> response = debts.stream().map(debtDTO -> MappingUtil.mapToNewClass(debtDTO, DebtResponse.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    /**
     * Returns a single debt
     *
     * @param userId: denotes the user to find debts for (TODO: REMOVE THIS WHEN AUTH IMPLEMENTED)
     * @param debtId: denotes the debt to search
     * @return Response Entity containing the debt details
     */
    @GetMapping(Routes.USER_PATH + "{userId}" + Routes.SINGLE_DEBT + "{debtId}")
    public ResponseEntity<DebtResponse> getSingleDebt(@PathVariable Long userId, @PathVariable Long debtId) {
        log.info("API GET {}{}{}{} called", Routes.USER_PATH, userId, Routes.SINGLE_DEBT, debtId);

        DebtDTO debtDTO = debtService.getDebtByDebtIdAndOwnerId(debtId, userId);

        return ResponseEntity.ok().body(MappingUtil.mapToNewClass(debtDTO, DebtResponse.class));

    }

    /**
     * Creates new debt in database
     *
     * @param debtRequest
     * @return debtResponse
     */
    @PostMapping(Routes.NEW_DEBT)
    public ResponseEntity<DebtResponse> saveExpenseDetails(@RequestBody @Valid DebtRequest debtRequest) {
        log.info("API POST /debts called for OwnerID: {}", debtRequest.getOwner());

        if (!DebtType.isValid(debtRequest.getDebtType())) {
            throw new InvalidDebtTypeException("Invalid Debt Type: " + debtRequest.getDebtType());
        }

        DebtDTO debtDTO = debtService.createNewDebt(debtRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(MappingUtil.mapToNewClass(debtDTO, DebtResponse.class));
    }
}
