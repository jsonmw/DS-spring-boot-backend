package com.debtsolver.DebtSolver.controller;

import com.debtsolver.DebtSolver.dto.UserDTO;
import com.debtsolver.DebtSolver.io.UserRequest;
import com.debtsolver.DebtSolver.io.UserResponse;
import com.debtsolver.DebtSolver.service.UserService;
import com.debtsolver.DebtSolver.util.MappingUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest) {
        log.info("POST on /register is called {}", userRequest);
        UserDTO userDTO = MappingUtil.mapToNewClass(userRequest, UserDTO.class);
        userDTO = userService.createNewUser(userDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(MappingUtil.mapToNewClass(userDTO, UserResponse.class));
    }

}
