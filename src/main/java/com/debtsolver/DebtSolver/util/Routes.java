package com.debtsolver.DebtSolver.util;

/**
 * Stores routing constants
 */
public class Routes {

    // user routes
    public static final String DASHBOARD = "/"; // separate in case the dashboard moves to a different endpoint
    public static final String HOME = "/";
    public static final String REGISTER = "/register";
    public static final String LOGIN = "/login";
    public static final String LOGOUT = "/logout";
    public static final String AUTH = "/auth";
    public static final String USER_PATH = "/user/";

    // debt routes
    public static final String ALL_DEBTS = "/debts";
    public static final String SINGLE_DEBT = "/debts/";
    public static final String NEW_DEBT = "/new";
}
