package com.chjcfcaloc2020.fafi.config;

import com.chjcfcaloc2020.fafi.filter.JwtAuthenticationFilter;
import com.chjcfcaloc2020.fafi.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        // league
                        .requestMatchers(HttpMethod.GET, "/api/leagues", "/api/leagues/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/leagues").hasAnyRole("ORGANIZER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/leagues/**").hasAnyRole("ORGANIZER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/leagues/**").hasAnyRole("ORGANIZER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/leagues/{leagueId}/teams/{teamId}").hasAnyRole("MANAGER", "ADMIN")
                        // team
                        .requestMatchers(HttpMethod.GET, "/api/teams", "/api/teams/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/teams", "/api/teams/**").hasAnyRole("MANAGER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/teams/**").hasAnyRole("MANAGER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/teams/**").hasAnyRole("MANAGER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/teams/my-teams", "/api/teams/{leagueId}/my-teams").hasAnyRole("MANAGER", "ADMIN")
                        // player
                        .requestMatchers(HttpMethod.GET, "/api/players", "/api/players/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/players").hasAnyRole("MANAGER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/leagues/**").hasAnyRole("MANAGER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/leagues/**").hasAnyRole("MANAGER", "ADMIN")
                        // invitation
                        .requestMatchers(HttpMethod.GET, "/api/invitations/**").hasAnyRole("ORGANIZER", "ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.POST, "/api/invitations").hasAnyRole("ORGANIZER", "ADMIN")
                        // match
                        .requestMatchers(HttpMethod.GET, "/api/matches", "/api/matches/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/matches", "/api/matches/**").hasAnyRole("ORGANIZER", "ADMIN")
                        // match-event
                        .requestMatchers(HttpMethod.GET, "/api/match-events", "/api/match-events/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/match-events").hasAnyRole("ORGANIZER", "ADMIN")
                        .anyRequest().permitAll()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
