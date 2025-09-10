package com.chjcfcaloc2020.fafi.dto.request;

import com.chjcfcaloc2020.fafi.common.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotNull(message = "Username is required")
    @NotBlank(message = "Please type username!")
    @Size(min = 6, message = "Username must be at least 6 characters!")
    private String username;

    @NotNull(message = "Password is required")
    @NotBlank(message = "Please type password!")
    @Size(min = 6, message = "Password must be at least 6 characters!")
    private String password;

    @Email(message = "Email should be valid")
    @NotNull(message = "Email is required")
    @NotBlank(message = "Please type email!")
    private String email;

    private String phone;
    private String address;

    @NotNull(message = "Role is required")
    private UserRole role;
}
