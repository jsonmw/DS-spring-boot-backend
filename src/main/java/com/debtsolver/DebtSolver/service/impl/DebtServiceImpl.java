package com.debtsolver.DebtSolver.service.impl;

import com.debtsolver.DebtSolver.dto.DebtDTO;
import com.debtsolver.DebtSolver.exception.ResourceNotFoundException;
import com.debtsolver.DebtSolver.mapping.EntityDTOMapper;
import com.debtsolver.DebtSolver.model.Debt;
import com.debtsolver.DebtSolver.repository.DebtRepository;
import com.debtsolver.DebtSolver.service.DebtService;
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

        return EntityDTOMapper.mapEntityToDTO(debt, DebtDTO.class);
    }

    /**
     * Retrieves all debts for a given user from database
     *
     * @param ownerId: the id of the owner for which to retrieve all debts
     * @return list
     */
    @Override
    public List<DebtDTO> getAllDebts(Long ownerId) {

        List<Debt> list = debtRepository.findByOwnerId(ownerId);
        log.info("Fetching all debts for user id: {} -> {}", ownerId, list);
        if (list.isEmpty()) {
            log.warn("No debts found for user id: {}", ownerId);
        }

        List<DebtDTO> listOfDebts = list.stream().map(debt -> EntityDTOMapper.mapEntityToDTO(debt, DebtDTO.class))
                .collect(Collectors.toList());

        return listOfDebts;
    }

}

