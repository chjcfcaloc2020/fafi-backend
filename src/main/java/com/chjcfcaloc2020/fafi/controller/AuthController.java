package com.chjcfcaloc2020.fafi.controller;

import com.chjcfcaloc2020.fafi.dto.request.LoginRequest;
import com.chjcfcaloc2020.fafi.dto.request.RegisterRequest;
import com.chjcfcaloc2020.fafi.dto.response.AuthResponse;
import com.chjcfcaloc2020.fafi.entity.User;
import com.chjcfcaloc2020.fafi.exception.payload.ResourceAlreadyExistsException;
import com.chjcfcaloc2020.fafi.repository.UserRepository;
import com.chjcfcaloc2020.fafi.util.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String accessToken = jwtTokenProvider.generateToken((UserDetails) authentication.getPrincipal());

            User user = userRepository.findById(loginRequest.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

            return ResponseEntity.ok(new AuthResponse(accessToken, user.getUsername(), user.getRole()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest registerRequest) {
        if (userRepository.existsById(registerRequest.getUsername())) {
            throw new ResourceAlreadyExistsException("Username already exists");
        }
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already exists");
        }

        User user = User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .phone(registerRequest.getPhone())
                .address(registerRequest.getAddress())
                .role(registerRequest.getRole())
                .build();
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfull");
    }
}
