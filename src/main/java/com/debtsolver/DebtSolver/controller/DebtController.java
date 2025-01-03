package com.debtsolver.DebtSolver.controller;

import com.debtsolver.DebtSolver.dto.DebtDTO;
import com.debtsolver.DebtSolver.io.DebtRequest;
import com.debtsolver.DebtSolver.io.DebtResponse;
import com.debtsolver.DebtSolver.service.DebtService;
import com.debtsolver.DebtSolver.util.MappingUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DebtController {

    private final DebtService debtService;
    /**
     * Creates new debt in database
     *
     * @param debtRequest
     * @return debtResponse
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/user/{ownerId}")
    public DebtResponse saveExpenseDetails(@RequestBody @Valid DebtRequest debtRequest, @PathVariable Long ownerId) {
        log.info("API POST /user/{} called {}", ownerId, debtRequest);
        DebtDTO debtDTO = MappingUtil.mapToNewClass(debtRequest, DebtDTO.class);
        debtDTO = debtService.createNewDebt(debtDTO);
        log.info("Printing the debt DTO {}", debtDTO);
        return MappingUtil.mapToNewClass(debtDTO, DebtResponse.class);
    }
}
