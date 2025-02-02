package com.debtsolver.DebtSolver.service;

import com.debtsolver.DebtSolver.exception.UserNotFoundException;
import com.debtsolver.DebtSolver.model.UserAccount;
import com.debtsolver.DebtSolver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public UserAccount getLoggedInUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String email = authentication.getName();

        log.info("Retrieved user associated with {}", email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("No UserAccount found for " + email));

    }
}
