package com.debtsolver.DebtSolver.exception;

import com.debtsolver.DebtSolver.io.Error;
import com.debtsolver.DebtSolver.util.ErrorCodes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for DebtSolver application
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Error> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return buildErrorResponse(
                ex,
                HttpStatus.NOT_FOUND,
                ErrorCodes.DATA_NOT_FOUND,
                getRequestPath(request),
                null);
    }

    @ExceptionHandler(InvalidDebtTypeException.class)
    public ResponseEntity<Error> handleInvalidDebtTypeException(InvalidDebtTypeException ex, WebRequest request) {
        return buildErrorResponse(ex,
                HttpStatus.BAD_REQUEST,
                ErrorCodes.INVALID_DEBT_TYPE,
                getRequestPath(request),
                null);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Error> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        return buildErrorResponse(
                ex,
                HttpStatus.NOT_FOUND,
                ErrorCodes.USER_NOT_FOUND,
                getRequestPath(request),
                null);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Error> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return buildErrorResponse(
                ex,
                HttpStatus.BAD_REQUEST,
                ErrorCodes.INVALID_ARGUMENT,
                getRequestPath(request),
                null);
    }

    @ExceptionHandler(ItemExistsException.class)
    public ResponseEntity<Error> handleItemExistsException(ItemExistsException ex, WebRequest request) {
        return buildErrorResponse(
                ex,
                HttpStatus.CONFLICT,
                ErrorCodes.ITEM_EXISTS,
                getRequestPath(request),
                null);
    }

    /**
     * Handles invalid Request Bodies that throw MethodArgumentNotValidExceptions
     *
     * @param ex: passed exception
     * @return ResponseEntity containing validity errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return buildErrorResponse(
                ex,
                HttpStatus.BAD_REQUEST,
                ErrorCodes.INVALID_ARGUMENT,
                getRequestPath(request),
                errors
        );
    }

    /**
     * Extract path from WebRequest
     *
     * @param request object
     * @return String containing the URI path
     */
    private String getRequestPath(WebRequest request) {
        return request.getDescription(false).replace("uri=", "");
    }

    /**
     * Constructs the Error object for returning in handler method
     *
     * @param ex:        the exception type to be constructed
     * @param status:    the status code to return
     * @param errorCode: the errorCode enum value
     * @param path:      the uri path on which the error was encountered
     * @param validationErrors:      optional validation errors
     * @return ResponseEntity containing the error object
     */
    private ResponseEntity<Error> buildErrorResponse(Exception ex, HttpStatus status, ErrorCodes errorCode, String path, Map<String, String> validationErrors) {
        Error error = Error.builder()
                .errorCode(errorCode.getCode())
                .statusCode(status.value())
                .message(ex.getMessage())
                .path(path)
                .timestamp(LocalDateTime.now())
                .validationErrors(validationErrors)
                .build();

        log.error("{} handled on path {}: {}", ex.getClass().getSimpleName(), path, ex.getMessage());
        return ResponseEntity.status(status).body(error);
    }

}
