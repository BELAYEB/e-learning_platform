package com.elearning.platform.modules.courses.api.dto;

public record CourseResponse(
        Long id,
        String title,
        String description,
        Long tutorId,
        Integer capacity,
        Boolean active
) {}
