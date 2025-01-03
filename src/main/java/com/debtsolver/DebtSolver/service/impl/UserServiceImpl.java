package com.debtsolver.DebtSolver.service.impl;

import com.debtsolver.DebtSolver.dto.UserDTO;
import com.debtsolver.DebtSolver.exception.ResourceNotFoundException;
import com.debtsolver.DebtSolver.mapping.EntityDTOMapper;
import com.debtsolver.DebtSolver.model.User;
import com.debtsolver.DebtSolver.repository.UserRepository;
import com.debtsolver.DebtSolver.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * Retrieves a single user by ID
     *
     * @param id:      the id of the user being retrieved
     * @return DTO containing the user details
     */
    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
        log.info("Returning User {}", user.getName());
        return EntityDTOMapper.mapEntityToDTO(user, UserDTO.class);
    }
}
