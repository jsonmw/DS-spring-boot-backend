package com.debtsolver.DebtSolver.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Error {
    private Integer statusCode;
    private String message;
    private String path;
    private LocalDateTime timestamp;
    private String errorCode;
    private Map<String, String> validationErrors;
}
