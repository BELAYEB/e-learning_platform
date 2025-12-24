package com.elearning.platform.modules.auth.security;

import com.elearning.platform.modules.auth.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT Authentication Filter
 * Intercepts every HTTP request to validate JWT tokens
 * and set up Spring Security authentication context
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // Extract JWT token from Authorization header
        String token = extractTokenFromRequest(request);

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Extract username from token
            String username = jwtService.extractUsername(token);

            // If username is valid and no authentication exists in context
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                authenticateUser(request, token, username);
            }
        } catch (Exception e) {
            log.error("Cannot set user authentication: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Extract JWT token from Authorization header
     */
    private String extractTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            return header.substring(7);
        }

        return null;
    }

    /**
     * Authenticate user and set security context
     */
    private void authenticateUser(HttpServletRequest request, String token, String username) {
        // Load user details
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Validate token
        if (jwtService.isTokenValid(token, userDetails.getUsername())) {
            // Create authentication token
            UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
                );

            // Set authentication details
            authToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
            );

            // Set authentication in security context
            SecurityContextHolder.getContext().setAuthentication(authToken);

            log.debug("Authenticated user: {}", username);
        }
    }
}
