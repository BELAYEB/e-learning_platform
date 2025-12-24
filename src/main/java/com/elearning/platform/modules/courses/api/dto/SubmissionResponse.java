package com.elearning.platform.modules.courses.api.dto;

import com.elearning.platform.modules.courses.domain.model.SubmissionStatus;

// SubmissionResponse.java
public record SubmissionResponse(
        Long id,
        Long assignmentId,
        Long studentId,
        String content,
        String attachmentPath,
        SubmissionStatus status,
        String submittedAt
) {}
