package com.elearning.platform.modules.auth.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Request DTO for password change
 */
public record ChangePasswordRequest(
        @Email @NotBlank String email,
        @NotBlank String currentPassword,
        @NotBlank String newPassword
) {}

