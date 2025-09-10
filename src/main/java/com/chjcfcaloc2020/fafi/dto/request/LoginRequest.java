package com.chjcfcaloc2020.fafi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
    @NotNull(message = "Username is required")
    @NotBlank(message = "Please type username!")
    @Size(min = 6, message = "Username must be at least 6 characters!")
    private String username;

    @NotNull(message = "Password is required")
    @NotBlank(message = "Please type password!")
    @Size(min = 6, message = "Password must be at least 6 characters!")
    private String password;
}
