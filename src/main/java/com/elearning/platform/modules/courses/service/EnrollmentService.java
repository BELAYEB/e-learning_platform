package com.elearning.platform.modules.courses.service;

import com.elearning.platform.modules.courses.api.dto.EnrollmentRequest;

public interface EnrollmentService {
    void enroll(Long courseId, EnrollmentRequest request);
}
