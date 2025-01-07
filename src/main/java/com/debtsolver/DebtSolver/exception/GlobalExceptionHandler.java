package com.debtsolver.DebtSolver.exception;

import com.debtsolver.DebtSolver.io.Error;
import com.debtsolver.DebtSolver.util.ErrorCodes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

/**
 * Global exception handler for DebtSolver application
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Error> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        String path = request.getDescription(false).replace("uri=", "");
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, ErrorCodes.DATA_NOT_FOUND, path);
    }

    @ExceptionHandler(InvalidDebtTypeException.class)
    public ResponseEntity<Error> handleInvalidDebtTypeException(InvalidDebtTypeException ex, WebRequest request) {
        String path = request.getDescription(false).replace("uri=", "");
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, ErrorCodes.INVALID_DEBT_TYPE, path);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Error> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        String path = request.getDescription(false).replace("uri=", "");
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, ErrorCodes.USER_NOT_FOUND, path);
    }

    /**
     *
     * Constructs the Error object for returning in handler method
     *
     * @param ex: the exception type to be constructed
     * @param status: the status code to return
     * @param errorCode: the errorCode enum value
     * @param path: the uri path on which the error was encountered
     * @return ResponseEntity containing the error object
     */
    private ResponseEntity<Error> buildErrorResponse(Exception ex, HttpStatus status, ErrorCodes errorCode, String path) {
        Error error = Error.builder()
                .errorCode(errorCode.getCode())
                .statusCode(status.value())
                .message(ex.getMessage())
                .path(path)
                .timestamp(LocalDateTime.now())
                .build();

        log.error("{} handled on path {}: {}", ex.getClass().getSimpleName(), path, ex.getMessage());
        return ResponseEntity.status(status).body(error);
    }

}
