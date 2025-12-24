package com.elearning.platform.modules.courses.service.impl;

import com.elearning.platform.common.exception.EnrollmentException;
import com.elearning.platform.common.exception.ResourceNotFoundException;
import com.elearning.platform.modules.courses.api.dto.EnrollmentRequest;
import com.elearning.platform.modules.courses.domain.model.Course;
import com.elearning.platform.modules.courses.domain.model.Enrollment;
import com.elearning.platform.modules.courses.domain.model.EnrollmentStatus;
import com.elearning.platform.modules.courses.domain.repository.CourseRepository;
import com.elearning.platform.modules.courses.domain.repository.EnrollmentRepository;
import com.elearning.platform.modules.courses.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class EnrollmentServiceImpl implements EnrollmentService {

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Override
    public void enroll(Long courseId, EnrollmentRequest request) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        long activeCount = courseRepository.countActiveEnrollments(courseId);
        if (course.isFull(activeCount)) {
            throw new EnrollmentException("Course is full");
        }

        boolean exists = enrollmentRepository
                .existsByCourseIdAndStudentIdAndStatus(courseId, request.studentId(), EnrollmentStatus.ACTIVE);
        if (exists) {
            throw new EnrollmentException("Student is already enrolled in this course");
        }

        Enrollment enrollment = Enrollment.builder()
                .courseId(courseId)
                .studentId(request.studentId())
                .status(EnrollmentStatus.ACTIVE)
                .enrolledAt(LocalDateTime.now())
                .build();

        enrollmentRepository.save(enrollment);
    }
}
