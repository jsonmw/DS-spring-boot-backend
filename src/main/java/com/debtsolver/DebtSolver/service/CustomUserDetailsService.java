package com.debtsolver.DebtSolver.service;

import com.debtsolver.DebtSolver.exception.UserNotFoundException;
import com.debtsolver.DebtSolver.model.UserAccount;
import com.debtsolver.DebtSolver.repository.UserRepository;
import com.debtsolver.DebtSolver.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UserNotFoundException {

        UserAccount userAccount = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(Constants.USER_NOT_FOUND + email));

        log.info("Inside loadByUserName()::: Printing UserAccount details {}", userAccount);
        return new User(userAccount.getEmail(), userAccount.getPassword(), new ArrayList<>());
    }
}
