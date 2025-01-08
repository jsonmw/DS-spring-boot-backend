package com.debtsolver.DebtSolver.service.impl;

import com.debtsolver.DebtSolver.dto.UserDTO;
import com.debtsolver.DebtSolver.exception.ItemExistsException;
import com.debtsolver.DebtSolver.exception.UserNotFoundException;
import com.debtsolver.DebtSolver.model.User;
import com.debtsolver.DebtSolver.repository.UserRepository;
import com.debtsolver.DebtSolver.service.UserService;
import com.debtsolver.DebtSolver.util.MappingUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Retrieves a single user by ID
     *
     * @param id: the id of the user being retrieved
     * @return DTO containing the user details
     */
    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No user found with ID: " + id));
        log.info("Returning User {} with ID {}", user.getName(), id);
        return MappingUtil.mapToNewClass(user, UserDTO.class);
    }

    /**
     * Creates new user in the database
     *
     * @param userDTO: the user to be added to the database
     * @return userDTO: user created upon success
     */
    @Override
    @Transactional
    public UserDTO createNewUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new ItemExistsException("A user account with this e-mail already exists: " + userDTO.getEmail());
        }

        User user = MappingUtil.mapToNewClass(userDTO, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        log.info("Created new user: {} - {}", userDTO.getId(), userDTO.getName());
        return MappingUtil.mapToNewClass(user, UserDTO.class);
    }


}
