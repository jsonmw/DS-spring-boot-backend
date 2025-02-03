package com.debtsolver.DebtSolver.exception;

import org.springframework.http.HttpStatus;

public class TokenException extends AuthException {
    public TokenException(String message, HttpStatus status) {
        super(message, status);
    }
}