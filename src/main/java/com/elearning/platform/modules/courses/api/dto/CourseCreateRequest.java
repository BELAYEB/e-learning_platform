package com.elearning.platform.modules.courses.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CourseCreateRequest(
        @NotBlank String title,
        String description,
        @NotNull Long tutorId,
        @NotNull @Min(1) Integer capacity
) {}
