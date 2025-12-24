package com.elearning.platform.infrastructure.config;

import com.elearning.platform.modules.auth.security.JwtAuthenticationEntryPoint;
import com.elearning.platform.modules.auth.security.JwtAuthenticationFilter;
import com.elearning.platform.modules.auth.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security Configuration for E-Learning Platform
 *
 * Features:
 * - JWT token-based authentication
 * - Stateless session management
 * - Role-based access control  (RBAC)
 * - Method-level security
 * - Development/Production profiles
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    @Value("${app.security.enabled:true}")
    private boolean securityEnabled;

    /**
     * Password encoder using BCrypt with strength 12
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    /**
     * Authentication provider configuration
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Authentication manager bean
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Production Security Configuration with JWT
     */
    @Bean
    @Profile("prod")
    public SecurityFilterChain prodSecurityFilterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtAuthFilter,
            JwtAuthenticationEntryPoint authEntryPoint) throws Exception {

        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Public endpoints
                .requestMatchers("/api/users/register", "/api/users/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/courses", "/api/courses/**").permitAll()

                // Admin endpoints
                .requestMatchers("/api/users/**").hasRole("ADMIN")

                // Tutor endpoints
                .requestMatchers(HttpMethod.POST, "/api/courses").hasAnyRole("TUTOR", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/courses/**").hasAnyRole("TUTOR", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/courses/**").hasAnyRole("TUTOR", "ADMIN")

                // All other requests require authentication
                .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex.authenticationEntryPoint(authEntryPoint))
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Development Security Configuration (with JWT and method security enabled)
     */
    @Bean
    @Profile("dev")
    public SecurityFilterChain devSecurityFilterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtAuthFilter,
            JwtAuthenticationEntryPoint authEntryPoint) throws Exception {

        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Allow all HTTP-level requests (but method security still applies)
                .anyRequest().permitAll()
            )
            .exceptionHandling(ex -> ex.authenticationEntryPoint(authEntryPoint))
            .authenticationProvider(authenticationProvider())
            // Add JWT filter to parse tokens and set authentication
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

