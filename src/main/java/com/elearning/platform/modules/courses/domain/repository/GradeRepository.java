package com.elearning.platform.modules.courses.domain.repository;

import com.elearning.platform.modules.courses.domain.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grade, Long> {

    Optional<Grade> findBySubmissionId(Long submissionId);

    boolean existsBySubmissionId(Long submissionId);
}
