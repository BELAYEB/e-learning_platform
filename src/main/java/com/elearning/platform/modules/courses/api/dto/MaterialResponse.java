// MaterialResponse.java
package com.elearning.platform.modules.courses.api.dto;

import com.elearning.platform.modules.courses.domain.model.MaterialType;

public record MaterialResponse(
        Long id,
        Long courseId,
        String title,
        MaterialType type,
        String storagePath,
        String uploadDate
) {}
