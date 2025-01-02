package com.debtsolver.DebtSolver.service.impl;

import com.debtsolver.DebtSolver.dto.DebtDTO;
import com.debtsolver.DebtSolver.exception.ResourceNotFoundException;
import com.debtsolver.DebtSolver.model.Debt;
import com.debtsolver.DebtSolver.repository.DebtRepository;
import com.debtsolver.DebtSolver.service.DebtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DebtServiceImpl implements DebtService {

    private final DebtRepository debtRepository;
    private final ModelMapper modelMapper;


    /**
     * Retrieves specified debt
     *
     * @param debtId: id for desired debt
     * @param ownerId: id for associated owner
     * @return DTO containing fetched debt
     */
    @Override
    public DebtDTO getDebtByDebtId(Long debtId, Long ownerId) {
        Debt debt = debtRepository.findByDebtIdAndOwnerId(debtId, ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Debt not found for given user"));

        log.info("Fetching debt {}", debt);

        return mapToDebtDTO(debt);
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

        List<DebtDTO> listOfDebts = list.stream().map(debt -> mapToDebtDTO(debt))
                .collect(Collectors.toList());

        return listOfDebts;
    }

    /**
     * Mapper method to convert expense entity to expense DTO
     *
     * @param debt: Debt object to be converted
     * @return resulting DebtDTO
     */
    private DebtDTO mapToDebtDTO(Debt debt) {
        return modelMapper.map(debt, DebtDTO.class);
    }

    /**
     * Mapper method to convert expense DTO to expense entity
     *
     * @param debt: DTO object to be converted
     * @return a Debt object from the DTO data
     */
    private Debt mapToDebtEntity(DebtDTO debt) {
        return modelMapper.map(debt, Debt.class);

    }
}

