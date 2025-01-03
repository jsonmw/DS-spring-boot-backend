package com.debtsolver.DebtSolver.service.impl;

import com.debtsolver.DebtSolver.dto.DebtDTO;
import com.debtsolver.DebtSolver.dto.UserDTO;
import com.debtsolver.DebtSolver.exception.ResourceNotFoundException;
import com.debtsolver.DebtSolver.model.Debt;
import com.debtsolver.DebtSolver.repository.DebtRepository;
import com.debtsolver.DebtSolver.service.DebtService;
import com.debtsolver.DebtSolver.service.UserService;
import com.debtsolver.DebtSolver.util.MappingUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DebtServiceImpl implements DebtService {

    private final DebtRepository debtRepository;
    private final UserService userService;

    /**
     * Retrieves specified debt
     *
     * @param debtId:  id for desired debt
     * @param ownerId: id for associated owner
     * @return DTO containing fetched debt
     */
    @Override
    public DebtDTO getDebtByDebtId(Long debtId, Long ownerId) {

        Debt debt = debtRepository.findByIdAndOwnerId(debtId, ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Debt not found for given user"));

        log.info("Fetching debt {}", debt);

        return MappingUtil.mapToNewClass(debt, DebtDTO.class);
    }

    /**
     * Retrieves all debts for a given user from database
     *
     * @param ownerId: the id of the owner for which to retrieve all debts
     * @return list of all debts associated with the given user
     */
    @Override
    public List<DebtDTO> getAllDebts(Long ownerId) {
        userService.getUserById(ownerId); // validate user existence or throw exception

        List<Debt> list = debtRepository.findByOwnerId(ownerId);
        log.info("Fetching all debts for user id: {} -> {}", ownerId, list);
        if (list.isEmpty()) {
            log.warn("No debts found for user id: {}", ownerId);
        }

        List<DebtDTO> listOfDebts = list.stream().map(debt -> MappingUtil.mapToNewClass(debt, DebtDTO.class))
                .collect(Collectors.toList());

        return listOfDebts;
    }

    /**
     * Creates new debt in the database
     *
     * @param debtDTO: a DTO representation of the debt details to be created
     * @return a DTO of the successfully created debt
     */
    @Override
    @Transactional
    public DebtDTO createNewDebt(DebtDTO debtDTO) {
        Long ownerId = debtDTO.getOwnerId();
        UserDTO user = userService.getUserById(ownerId); // validate user existence or throw exception
        log.info("Retrieved User {} by id {}", user.getName(), ownerId);

        Debt debt = MappingUtil.mapToNewClass(debtDTO, Debt.class);
        debt = debtRepository.save(debt);
        log.info("Created new debt {}", debt);

        return MappingUtil.mapToNewClass(debt, DebtDTO.class);
    }

}

