package com.elearning.platform.modules.auth.api.dto;

public record UserResponse(
        Long id,
        String username,
        String email,
        String role,
        Boolean active
) {}
