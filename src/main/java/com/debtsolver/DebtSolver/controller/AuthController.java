package com.debtsolver.DebtSolver.controller;

import com.debtsolver.DebtSolver.dto.UserDTO;
import com.debtsolver.DebtSolver.io.AuthRequest;
import com.debtsolver.DebtSolver.io.AuthResponse;
import com.debtsolver.DebtSolver.io.UserRequest;
import com.debtsolver.DebtSolver.io.UserResponse;
import com.debtsolver.DebtSolver.service.UserService;
import com.debtsolver.DebtSolver.util.JwtUtil;
import com.debtsolver.DebtSolver.util.MappingUtil;
import com.debtsolver.DebtSolver.util.Routes;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping(Routes.REGISTER)
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest) {
        log.info("POST on /register is called {}", userRequest);
        UserDTO userDTO = MappingUtil.mapToNewClass(userRequest, UserDTO.class);
        UserResponse response = userService.createNewUser(userDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping(Routes.LOGIN)
    public AuthResponse authenticateLogin(@RequestBody AuthRequest authRequest) throws Exception {
        log.info("POST on /login is called {}", authRequest.getEmail());
        String email = authRequest.getEmail();

        authenticate(authRequest);
        final String token = jwtUtil.generateToken(email);

        return new AuthResponse(token, email);
    }

    @DeleteMapping(Routes.USER_PATH + "{id}")
    public ResponseEntity<Object> deleteUserById(@PathVariable Long id) {
        log.info("DELETE on" + Routes.USER_PATH + "{} called", id);
        userService.deleteUserById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("UserAccount successfully deleted with id " + id);
    }

    private void authenticate(AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );

            log.info("Successfully authenticated user: {}", authRequest.getEmail());
        } catch (DisabledException ex) {
            log.warn("Failed login attempt - Disabled Account for user : {}", authRequest.getEmail());
            throw new AuthException("Invalid User");
        } catch (BadCredentialsException e) {
            log.warn("Failed login attempt - Incorrect credentials for user : {}", authRequest.getEmail());
            throw new AuthException("Username or password not accepted");
        }
    }

}
