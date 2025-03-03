package com.debtsolver.DebtSolver.controller;

import com.debtsolver.DebtSolver.dto.UserDTO;
import com.debtsolver.DebtSolver.io.AuthRequest;
import com.debtsolver.DebtSolver.io.AuthResponse;
import com.debtsolver.DebtSolver.io.UserRequest;
import com.debtsolver.DebtSolver.io.UserResponse;
import com.debtsolver.DebtSolver.service.AuthService;
import com.debtsolver.DebtSolver.service.TokenBlacklistService;
import com.debtsolver.DebtSolver.service.UserService;
import com.debtsolver.DebtSolver.util.JwtUtil;
import com.debtsolver.DebtSolver.util.MappingUtil;
import com.debtsolver.DebtSolver.util.Routes;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
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
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final TokenBlacklistService tokenBlacklistService;
    private final JwtUtil jwtUtil;

    /**
     * API endpoint to register new user
     *
     * @param UserRequest : contains information about new user
     * @return UserResponse : confirms creation successful
     */
    @PostMapping(Routes.REGISTER)
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest) {
        log.info("POST on /register is called {}", userRequest);
        UserDTO userDTO = MappingUtil.mapToNewClass(userRequest, UserDTO.class);
        UserResponse response = userService.createNewUser(userDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * API endpoint to authenticate login and generate token
     *
     * @param AuthRequest : contains credentials
     * @return AuthResponse : confirms username and returns new token
     */
    @PostMapping(Routes.LOGIN)
    public ResponseEntity<AuthResponse> authenticateLogin(@RequestBody AuthRequest authRequest) throws Exception {
        log.info("POST on /login is called {}", authRequest.getEmail());
        String email = authRequest.getEmail();

        authenticate(authRequest);
        final String token = jwtUtil.generateToken(email);

        return ResponseEntity.status(HttpStatus.OK).body(new AuthResponse(token, email));
    }

    /**
     * API endpoint to facilitate logging out and adding a JWT to the blacklist
     *
     * @param request : POST request containing the token
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(Routes.LOGOUT)
    public void logout(HttpServletRequest request) {
        log.info("POST on /logout is called for user {}", authService.getLoggedInUser().getName());

        String token = jwtUtil.extractTokenFromRequest(request);

        if(token != null) {
            tokenBlacklistService.addTokenToBlacklist(token);
            log.info("{} added to blacklist", token);
        }
    }

    /**
     * Removes a user
     * @param id
     * @return
     */
    @DeleteMapping(Routes.USER_PATH + "{id}")
    public ResponseEntity<Object> deleteUserById(@PathVariable Long id) {
        log.info("DELETE on" + Routes.USER_PATH + "{} called", id);
        userService.deleteUserById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("UserAccount successfully deleted with id " + id);
    }

    /**
     * Validates an authentication request
     *
     * @param authRequest : request with username and password to be authenticated
     * @throws AuthException : Bad Credentials or Disabled User
     */
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
