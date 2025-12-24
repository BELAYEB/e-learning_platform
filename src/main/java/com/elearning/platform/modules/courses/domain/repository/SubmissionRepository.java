package com.elearning.platform.modules.courses.domain.repository;

import com.elearning.platform.modules.courses.domain.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    Optional<Submission> findByAssignmentIdAndStudentId(Long assignmentId, Long studentId);

    List<Submission> findByAssignmentId(Long assignmentId);

    boolean existsByAssignmentIdAndStudentId(Long assignmentId, Long studentId);
}
