package com.elearning.platform.modules.courses.service;

import com.elearning.platform.modules.courses.api.dto.GradeRequest;
import com.elearning.platform.modules.courses.domain.model.Grade;

public interface GradingService {
    Grade gradeSubmission(Long submissionId, GradeRequest request);
}
