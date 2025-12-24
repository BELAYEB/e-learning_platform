package com.elearning.platform.modules.courses.api.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AssignmentCreateRequest(
        @NotBlank String title,
        String description,
        @NotNull @Future LocalDateTime dueDate,
        @NotNull @Min(1) Integer maxScore
) {}
