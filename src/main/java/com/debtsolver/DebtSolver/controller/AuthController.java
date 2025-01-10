package com.debtsolver.DebtSolver.controller;

import com.debtsolver.DebtSolver.dto.UserDTO;
import com.debtsolver.DebtSolver.io.UserRequest;
import com.debtsolver.DebtSolver.io.UserResponse;
import com.debtsolver.DebtSolver.service.UserService;
import com.debtsolver.DebtSolver.util.MappingUtil;
import com.debtsolver.DebtSolver.util.Routes;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping(Routes.REGISTER)
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest) {
        log.info("POST on /register is called {}", userRequest);
        UserDTO userDTO = MappingUtil.mapToNewClass(userRequest, UserDTO.class);
        UserResponse response = userService.createNewUser(userDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @DeleteMapping(Routes.USER_PATH + "{id}")
    public ResponseEntity<Object> deleteUserById(@PathVariable Long id) {
        log.info("DELETE on" + Routes.USER_PATH + "{} called", id);
        userService.deleteUserById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User successfully deleted with id " + id);
    }

}
