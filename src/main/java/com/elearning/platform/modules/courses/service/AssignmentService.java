package com.elearning.platform.modules.courses.service;

import com.elearning.platform.modules.courses.api.dto.AssignmentCreateRequest;
import com.elearning.platform.modules.courses.domain.model.Assignment;

import java.util.List;

public interface AssignmentService {
    Assignment createAssignment(Long courseId, AssignmentCreateRequest request);
    List<Assignment> listAssignments(Long courseId);
}
