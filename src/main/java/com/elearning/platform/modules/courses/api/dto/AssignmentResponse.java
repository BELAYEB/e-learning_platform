package com.elearning.platform.modules.courses.api.dto;

// AssignmentResponse.java
public record AssignmentResponse(
        Long id,
        Long courseId,
        String title,
        String description,
        String dueDate,
        Integer maxScore
) {}