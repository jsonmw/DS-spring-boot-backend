package com.debtsolver.DebtSolver.util;

/**
 * Stores constants of DebtType and provides validation method
 *
 */
public enum DebtType {
    CARD("card"),
    LOAN("loan");

    private final String value;

    DebtType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean isValid(String value) {
        for (DebtType debtType : values()) {
            if (debtType.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
