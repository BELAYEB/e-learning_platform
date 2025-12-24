package com.elearning.platform.modules.courses.domain.repository;

import com.elearning.platform.modules.courses.domain.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByTutorId(Long tutorId);

    @Query("select count(e) from Enrollment e where e.courseId = :courseId and e.status = 'ACTIVE'")
    long countActiveEnrollments(Long courseId);
}
