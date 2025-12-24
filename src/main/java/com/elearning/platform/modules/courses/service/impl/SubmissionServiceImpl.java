package com.elearning.platform.modules.courses.service.impl;

import com.elearning.platform.common.exception.BadRequestException;
import com.elearning.platform.common.exception.ResourceNotFoundException;
import com.elearning.platform.modules.courses.api.dto.SubmissionRequest;
import com.elearning.platform.modules.courses.domain.model.Assignment;
import com.elearning.platform.modules.courses.domain.model.Submission;
import com.elearning.platform.modules.courses.domain.model.SubmissionStatus;
import com.elearning.platform.modules.courses.domain.repository.AssignmentRepository;
import com.elearning.platform.modules.courses.domain.repository.SubmissionRepository;
import com.elearning.platform.modules.courses.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final AssignmentRepository assignmentRepository;

    @Override
    public Submission submit(Long assignmentId, SubmissionRequest request) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found"));

        // Check if assignment is past due date
        if (assignment.getDueDate() != null && LocalDateTime.now().isAfter(assignment.getDueDate())) {
            throw new BadRequestException("Assignment submission deadline has passed");
        }

        // Check for duplicate submission
        boolean alreadySubmitted = submissionRepository
                .existsByAssignmentIdAndStudentId(assignmentId, request.studentId());
        if (alreadySubmitted) {
            throw new BadRequestException("Student has already submitted this assignment");
        }

        Submission submission = Submission.builder()
                .assignmentId(assignmentId)
                .studentId(request.studentId())
                .content(request.content())
                .attachmentPath(request.attachmentPath())
                .status(SubmissionStatus.SUBMITTED)
                .submittedAt(LocalDateTime.now())
                .build();
        return submissionRepository.save(submission);
    }
}
