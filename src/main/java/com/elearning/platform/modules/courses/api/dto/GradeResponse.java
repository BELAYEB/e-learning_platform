package com.elearning.platform.modules.courses.api.dto;

// GradeResponse.java
public record GradeResponse(
        Long id,
        Long submissionId,
        Integer score,
        String feedback,
        Long gradedByTutorId,
        String gradedAt
) {}