package com.debtsolver.DebtSolver.controller;

import com.debtsolver.DebtSolver.io.HelloWorld;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DebtController {

    @GetMapping("/helloworld")
    public HelloWorld helloWorld() {
        HelloWorld response = new HelloWorld();
        response.setText("Hello World");
        return response;
    }
}
