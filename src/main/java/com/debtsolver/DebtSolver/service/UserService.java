package com.debtsolver.DebtSolver.service;

import com.debtsolver.DebtSolver.dto.UserDTO;

/**
 * Service interface for User objects
 *
 * @author Jason Wild
 */
public interface UserService {

    /**
     * Retrieves a single user by ID
     *
     * @param id: numeric associated with the given debt
     * @return DTO containing the user details
     */
    UserDTO getUserById(Long id);

    /**
     * Creates new user in the database
     *
     * @param userDTO: the user to be added to the database
     * @return userDTO: user created upon success
     */
    UserDTO createNewUser(UserDTO userDTO);

}
