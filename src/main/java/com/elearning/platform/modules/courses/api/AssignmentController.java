package com.elearning.platform.modules.courses.api;

import com.elearning.platform.modules.courses.api.dto.AssignmentCreateRequest;
import com.elearning.platform.modules.courses.api.dto.GradeRequest;
import com.elearning.platform.modules.courses.api.dto.SubmissionRequest;
import com.elearning.platform.modules.courses.domain.model.Assignment;
import com.elearning.platform.modules.courses.domain.model.Grade;
import com.elearning.platform.modules.courses.domain.model.Submission;
import com.elearning.platform.modules.courses.service.AssignmentService;
import com.elearning.platform.modules.courses.service.GradingService;
import com.elearning.platform.modules.courses.service.SubmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses/{courseId}")
@RequiredArgsConstructor
@Slf4j
public class AssignmentController {

    private final AssignmentService assignmentService;
    private final SubmissionService submissionService;
    private final GradingService gradingService;

    @PostMapping("/assignments")
    public ResponseEntity<Assignment> createAssignment(
            @PathVariable Long courseId,
            @Valid @RequestBody AssignmentCreateRequest request) {
        Assignment created = assignmentService.createAssignment(courseId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/assignments")
    public ResponseEntity<List<Assignment>> listAssignments(@PathVariable Long courseId) {
        return ResponseEntity.ok(assignmentService.listAssignments(courseId));
    }

    @PostMapping("/assignments/{assignmentId}/submissions")
    public ResponseEntity<Submission> submitAssignment(
            @PathVariable Long assignmentId,
            @Valid @RequestBody SubmissionRequest request) {
        Submission submission = submissionService.submit(assignmentId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(submission);
    }

    @PostMapping("/submissions/{submissionId}/grade")
    public ResponseEntity<Grade> gradeSubmission(
            @PathVariable Long submissionId,
            @Valid @RequestBody GradeRequest request) {
        Grade grade = gradingService.gradeSubmission(submissionId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(grade);
    }
}
