package com.debtsolver.DebtSolver.service;

import com.debtsolver.DebtSolver.model.UserAccount;

public interface AuthService {

    UserAccount getLoggedInUser();
}
