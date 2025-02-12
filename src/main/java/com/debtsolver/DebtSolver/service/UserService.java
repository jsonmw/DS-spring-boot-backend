package com.debtsolver.DebtSolver.service;

import com.debtsolver.DebtSolver.dto.UserDTO;
import com.debtsolver.DebtSolver.io.UserResponse;

/**
 * Service interface for UserAccount objects
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

    UserDTO getUserByEmail(String email);

    /**
     * Creates new user in the database
     *
     * @param userDTO: the user to be added to the database
     * @return userDTO: user created upon success
     */
    UserResponse createNewUser(UserDTO userDTO);

    /**
     * Deletes user from the database
     *
     * @param id: the ID of the user to be deleted
     * @return void
     */
    void deleteUserById(Long id);

}
