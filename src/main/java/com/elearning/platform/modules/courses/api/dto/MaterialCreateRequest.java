// MaterialCreateRequest.java
package com.elearning.platform.modules.courses.api.dto;

import com.elearning.platform.modules.courses.domain.model.MaterialType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MaterialCreateRequest(
        @NotNull Long courseId,
        @NotBlank String title,
        @NotBlank String storagePath,
        MaterialType type
) {}
