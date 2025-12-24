package com.elearning.platform.modules.courses.api.dto;

import jakarta.validation.constraints.NotNull;

public record EnrollmentRequest(
        @NotNull Long studentId
) {}
