package com.debtsolver.DebtSolver.service.impl;

import com.debtsolver.DebtSolver.dto.CardDTO;
import com.debtsolver.DebtSolver.dto.DebtDTO;
import com.debtsolver.DebtSolver.dto.LoanDTO;
import com.debtsolver.DebtSolver.exception.InvalidDebtTypeException;
import com.debtsolver.DebtSolver.exception.ResourceNotFoundException;
import com.debtsolver.DebtSolver.io.DebtRequest;
import com.debtsolver.DebtSolver.model.Card;
import com.debtsolver.DebtSolver.model.Debt;
import com.debtsolver.DebtSolver.model.Loan;
import com.debtsolver.DebtSolver.model.UserAccount;
import com.debtsolver.DebtSolver.repository.DebtRepository;
import com.debtsolver.DebtSolver.service.AuthService;
import com.debtsolver.DebtSolver.service.DebtService;
import com.debtsolver.DebtSolver.service.UserService;
import com.debtsolver.DebtSolver.util.DebtType;
import com.debtsolver.DebtSolver.util.MappingUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DebtServiceImpl implements DebtService {

    private final DebtRepository debtRepository;
    private final AuthService authService;
    private final UserService userService;

    /**
     * Retrieves specified debt
     *
     * @param debtId:  id for desired debt
     * @return DTO containing fetched debt
     */
    @Override
    public DebtDTO getDebtById(Long debtId) {
        Long ownerId = authService.getLoggedInUser().getId();

        Debt debt = debtRepository.findByIdAndOwner_Id(debtId, ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Debt not found for given user"));

        log.info("Fetching debt {} for user {}", debt.getName(), userService.getUserById(ownerId).getName());

        return MappingUtil.mapToNewClass(debt, DebtDTO.class);
    }

    /**
     * Retrieves all debts for a given user from database
     *
     * @return list of all debts associated with the given user
     */
    @Override
    public List<DebtDTO> getAllDebts() {
        Long ownerId = authService.getLoggedInUser().getId();
        List<Debt> list = debtRepository.findByOwnerId(ownerId);

        if (list.isEmpty()) {
            log.warn("No debts found for user id: {}", ownerId);
        }

        return list.stream()
                .map(debt -> {
                    if (debt instanceof Card) {

                        CardDTO cardDTO = MappingUtil.mapToNewClass(debt, CardDTO.class);
                        cardDTO.setDebtType(DebtType.CARD);
                        return cardDTO;
                    } else if (debt instanceof Loan) {
                        LoanDTO loanDTO = MappingUtil.mapToNewClass(debt, LoanDTO.class);
                        loanDTO.setDebtType(DebtType.LOAN);
                        return loanDTO;
                    } else {
                        throw new InvalidDebtTypeException("Invalid debt type: " + debt.getClass());
                    }
                })
                .collect(Collectors.toList());

    }

    /**
     * Creates new debt in the database
     *
     * @param debtRequest: a Request object representation of the debt details to be created
     * @return a DTO of the successfully created debt
     */
    @Override
    @Transactional
    public DebtDTO createNewDebt(DebtRequest debtRequest) {
        Long ownerId = authService.getLoggedInUser().getId();
        UserAccount owner = MappingUtil.mapToNewClass(userService.getUserById(ownerId), UserAccount.class);

        DebtType debtType;
        try {
            debtType = DebtType.valueOf(debtRequest.getDebtType().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidDebtTypeException("Invalid Debt Type: " + debtRequest.getDebtType());
        }

        Debt debt = extractDebt(debtRequest, debtType);
        debt.setOwner(owner);

        debtRepository.save(debt);

        return getDebtDTOType(debt);
    }

    /**
     * Deletes debt from the Database
     *
     * @param id: the id of the debt to be deleted
     * @return void
     */
    @Transactional
    public void deleteDebtById(Long id) {
        try {
            getDebtById(id); // validate debt exists and belongs to logged-in user

            Debt debt = debtRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Debt with ID " + id + " not found"));
            debt.getOwner().getDebts().remove(debt);
            debtRepository.delete(debt);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Debt with ID " + id + " not found");
        }
    }

    // Private helper methods

    /**
     * Extracts Debt from Request and sets correct DebtType
     *
     * @param debtRequest: a Request object representation of the debt details to be created
     * @param debtType:    type to be set
     * @return Debt with correct DebtType set
     */
    private Debt extractDebt(DebtRequest debtRequest, DebtType debtType) {
        switch (debtType) {
            case CARD:
                Debt cardDebt = MappingUtil.mapToNewClass(debtRequest, Card.class);
                cardDebt.setDebtType(DebtType.CARD);  // Explicitly set the debt type
                return cardDebt;
            case LOAN:
                Debt loanDebt = MappingUtil.mapToNewClass(debtRequest, Loan.class);
                loanDebt.setDebtType(DebtType.LOAN);  // Explicitly set the debt type
                return loanDebt;
            default:
                throw new InvalidDebtTypeException("Invalid Debt Type: " + debtRequest.getDebtType());
        }
    }

    /**
     * Returns correct DTO based on type of debt
     *
     * @param debt: Debt to be converted
     * @return Debt DTO of correct class
     */
    private DebtDTO getDebtDTOType(Debt debt) {
        if (debt instanceof Card) {
            return MappingUtil.mapToNewClass(debt, CardDTO.class);
        } else if (debt instanceof Loan) {
            return MappingUtil.mapToNewClass(debt, LoanDTO.class);
        }
        return null;
    }

}

