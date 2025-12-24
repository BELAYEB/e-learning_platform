package com.elearning.platform.modules.courses.domain.repository;

import com.elearning.platform.modules.courses.domain.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByCourseId(Long courseId);
}
