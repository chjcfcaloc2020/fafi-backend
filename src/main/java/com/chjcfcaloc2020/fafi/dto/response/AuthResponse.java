package com.chjcfcaloc2020.fafi.dto.response;

import com.chjcfcaloc2020.fafi.common.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String accessToken;
    private String userName;
    private UserRole role;
}
