package com.debtsolver.DebtSolver.io;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    @NotBlank(message="Name is required")
    @Size(min = 3, message = "Name must be at least three characters")
    private String name;

    @NotBlank(message = "Valid e-mail is required")
    @Email(message = "Valid e-mail is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min=8, message = "Password must be at least 8 characters")
    private String password;

}
