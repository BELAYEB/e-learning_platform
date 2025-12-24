package com.elearning.platform.modules.auth.api.dto;

public record LoginResponse(
        Long userId,
        String username,
        String email,
        String role,
        String token // null or placeholder until JWT is added
) {}
