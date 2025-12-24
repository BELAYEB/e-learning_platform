package com.elearning.platform.modules.courses.domain.repository;

import com.elearning.platform.modules.courses.domain.model.Enrollment;
import com.elearning.platform.modules.courses.domain.model.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    boolean existsByCourseIdAndStudentIdAndStatus(Long courseId, Long studentId, EnrollmentStatus status);

    List<Enrollment> findByStudentIdAndStatus(Long studentId, EnrollmentStatus status);

    Optional<Enrollment> findByCourseIdAndStudentId(Long courseId, Long studentId);
}
