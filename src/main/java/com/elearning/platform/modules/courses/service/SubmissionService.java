package com.elearning.platform.modules.courses.service;

import com.elearning.platform.modules.courses.api.dto.SubmissionRequest;
import com.elearning.platform.modules.courses.domain.model.Submission;

public interface SubmissionService {
    Submission submit(Long assignmentId, SubmissionRequest request);
}
