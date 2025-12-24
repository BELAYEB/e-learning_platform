package com.elearning.platform.modules.courses.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GradeRequest(
        @NotNull Long tutorId,
        @NotNull @Min(0) @Max(100) Integer score,
        @NotBlank String feedback
) {}
