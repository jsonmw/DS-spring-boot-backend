package com.debtsolver.DebtSolver.controller;

import com.debtsolver.DebtSolver.dto.DebtDTO;
import com.debtsolver.DebtSolver.exception.InvalidDebtTypeException;
import com.debtsolver.DebtSolver.io.DebtRequest;
import com.debtsolver.DebtSolver.io.DebtResponse;
import com.debtsolver.DebtSolver.service.AuthService;
import com.debtsolver.DebtSolver.service.DebtService;
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
    private final AuthService authService;

    /**
     * Returns a list of all debts associated with the logged in user
     *
     * @return Response Entity containing a list of Debts
     */
    @GetMapping(Routes.ALL_DEBTS)
    public ResponseEntity<List<DebtResponse>> getDebts() {
        log.info("API GET /debts called");
        List<DebtDTO> debts = debtService.getAllDebts();
        log.info("Found {} debts for {}", debts.size(), authService.getLoggedInUser().getName());
        List<DebtResponse> response = debts.stream().map(debtDTO -> MappingUtil.mapToNewClass(debtDTO, DebtResponse.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    /**
     * Returns a single debt
     *
     * @param debtId: denotes the debt to search
     * @return Response Entity containing the debt details
     */
    @GetMapping(Routes.SINGLE_DEBT + "{debtId}")
    public ResponseEntity<DebtResponse> getSingleDebt(@PathVariable Long debtId) {
        log.info("API GET {}/{} called", Routes.SINGLE_DEBT, debtId);

        DebtDTO debtDTO = debtService.getDebtById(debtId);

        return ResponseEntity.ok().body(MappingUtil.mapToNewClass(debtDTO, DebtResponse.class));

    }

    /**
     * Creates new debt in database
     *
     * @param debtRequest
     * @return debtResponse and 201 statuscode
     */
    @PostMapping(Routes.NEW_DEBT)
    public ResponseEntity<DebtResponse> saveDebtDetails(@RequestBody @Valid DebtRequest debtRequest) {
        log.info("API POST {} called for OwnerID: {}", Routes.NEW_DEBT, authService.getLoggedInUser());

        if (!DebtType.isValid(debtRequest.getDebtType())) {
            throw new InvalidDebtTypeException("Invalid Debt Type: " + debtRequest.getDebtType());
        }

        DebtDTO debtDTO = debtService.createNewDebt(debtRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(MappingUtil.mapToNewClass(debtDTO, DebtResponse.class));
    }

    /**
     * Updates existing debt in database
     *
     * @param debtRequest
     * @return debtResponse and 200 status code
     */
    @PutMapping(Routes.SINGLE_DEBT + "{debtId")
    public ResponseEntity<DebtResponse> updateDebtDetails(@RequestBody @Valid DebtRequest updateRequest, @PathVariable Long debtId) {
        log.info("API PUT {}{} request: {}", Routes.SINGLE_DEBT, debtId, updateRequest);
        DebtDTO debtDTO = MappingUtil.mapToNewClass(updateRequest, DebtDTO.class);
        debtDTO = debtService.updateDebtDetails(debtDTO, debtId);

        return ResponseEntity.status(HttpStatus.OK).body(MappingUtil.mapToNewClass(debtDTO, DebtResponse.class));
    }

    /**
     * Delete debt from database
     *
     * @param debtId: numeric ID for the debt to be deleted
     * @return: void
     */
    @DeleteMapping(Routes.SINGLE_DEBT + "{debtId}")
    public ResponseEntity<Object> deleteDebt(@PathVariable Long debtId) {
        log.info("API DELETE {}{} called", Routes.SINGLE_DEBT, debtId);

        debtService.deleteDebtById(debtId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Debt successfully deleted with id " + debtId);
    }
}
