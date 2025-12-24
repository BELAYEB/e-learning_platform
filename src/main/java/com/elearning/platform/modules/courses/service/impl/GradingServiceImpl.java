package com.elearning.platform.modules.courses.service.impl;

import com.elearning.platform.common.exception.BadRequestException;
import com.elearning.platform.common.exception.ResourceNotFoundException;
import com.elearning.platform.modules.courses.api.dto.GradeRequest;
import com.elearning.platform.modules.courses.domain.model.Assignment;
import com.elearning.platform.modules.courses.domain.model.Grade;
import com.elearning.platform.modules.courses.domain.model.Submission;
import com.elearning.platform.modules.courses.domain.repository.AssignmentRepository;
import com.elearning.platform.modules.courses.domain.repository.GradeRepository;
import com.elearning.platform.modules.courses.domain.repository.SubmissionRepository;
import com.elearning.platform.modules.courses.service.GradingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class GradingServiceImpl implements GradingService {

    private final SubmissionRepository submissionRepository;
    private final GradeRepository gradeRepository;
    private final AssignmentRepository assignmentRepository;

    @Override
    public Grade gradeSubmission(Long submissionId, GradeRequest request) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new ResourceNotFoundException("Submission not found"));

        // Get the assignment to validate score
        Assignment assignment = assignmentRepository.findById(submission.getAssignmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found"));

        // Validate score
        if (request.score() < 0) {
            throw new BadRequestException("Score cannot be negative");
        }

        if (assignment.getMaxScore() != null && request.score() > assignment.getMaxScore()) {
            throw new BadRequestException("Score cannot exceed maximum score of " + assignment.getMaxScore());
        }

        // Check if already graded
        if (gradeRepository.existsBySubmissionId(submissionId)) {
            throw new BadRequestException("Submission has already been graded");
        }

        Grade grade = Grade.builder()
                .submissionId(submission.getId())
                .score(request.score())
                .feedback(request.feedback())
                .gradedByTutorId(request.tutorId())
                .gradedAt(LocalDateTime.now())
                .build();

        return gradeRepository.save(grade);
    }
}
