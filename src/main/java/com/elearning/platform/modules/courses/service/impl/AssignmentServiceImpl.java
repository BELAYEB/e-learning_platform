package com.elearning.platform.modules.courses.service.impl;

import com.elearning.platform.common.exception.BadRequestException;
import com.elearning.platform.modules.courses.api.dto.AssignmentCreateRequest;
import com.elearning.platform.modules.courses.domain.model.Assignment;
import com.elearning.platform.modules.courses.domain.repository.AssignmentRepository;
import com.elearning.platform.modules.courses.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;

    @Override
    public Assignment createAssignment(Long courseId, AssignmentCreateRequest request) {
        if (request.dueDate() != null && request.dueDate().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Due date cannot be in the past");
        }

        if (request.maxScore() != null && request.maxScore() <= 0) {
            throw new BadRequestException("Max score must be greater than 0");
        }

        Assignment assignment = Assignment.builder()
                .courseId(courseId)
                .title(request.title())
                .description(request.description())
                .dueDate(request.dueDate())
                .maxScore(request.maxScore())
                .build();
        return assignmentRepository.save(assignment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Assignment> listAssignments(Long courseId) {
        return assignmentRepository.findByCourseId(courseId);
    }
}
