package com.debtsolver.DebtSolver.service.impl;

import com.debtsolver.DebtSolver.exception.UserNotFoundException;
import com.debtsolver.DebtSolver.model.UserAccount;
import com.debtsolver.DebtSolver.repository.UserRepository;
import com.debtsolver.DebtSolver.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public UserAccount getLoggedInUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String email = authentication.getName();

        log.info("Retrieved user associated with {}", email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("No UserAccount found for " + email));
    }
}
