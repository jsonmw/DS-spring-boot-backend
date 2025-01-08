package com.debtsolver.DebtSolver.util;

/**
 * Stores constants for exception handler errorCodes
 */
public enum ErrorCodes {
    DATA_NOT_FOUND("DATA_NOT_FOUND"),
    INVALID_DEBT_TYPE("INVALID_DEBT_TYPE"),
    USER_NOT_FOUND("USER_NOT_FOUND"),
    INVALID_ARGUMENT("INVALID_ARGUMENT"),
    ITEM_EXISTS("ITEM_EXISTS"),
    UNKNOWN_ERROR("UNKNOWN_ERROR");

    private final String code;

    ErrorCodes(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}