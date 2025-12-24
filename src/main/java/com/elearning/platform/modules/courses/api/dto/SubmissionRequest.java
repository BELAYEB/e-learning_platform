package com.elearning.platform.modules.courses.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SubmissionRequest(
        @NotNull Long studentId,
        @NotBlank String content,
        String attachmentPath
) {}
