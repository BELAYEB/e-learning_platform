package com.elearning.platform.modules.courses.api;

import com.elearning.platform.modules.courses.api.dto.CourseCreateRequest;
import com.elearning.platform.modules.courses.api.dto.CourseResponse;
import com.elearning.platform.modules.courses.api.dto.EnrollmentRequest;
import com.elearning.platform.modules.courses.service.CourseService;
import com.elearning.platform.modules.courses.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@Slf4j
public class CourseController {

    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<CourseResponse> createCourse(
            @Valid @RequestBody CourseCreateRequest request) {
        log.info("Creating course with title: {}", request.title());
        CourseResponse response = courseService.createCourse(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> getCourse(@PathVariable Long id) {
        log.info("Fetching course with id: {}", id);
        return ResponseEntity.ok(courseService.getCourse(id));
    }

    @GetMapping("/tutor/{tutorId}")
    public ResponseEntity<List<CourseResponse>> getTutorCourses(@PathVariable Long tutorId) {
        log.info("Fetching courses for tutor: {}", tutorId);
        return ResponseEntity.ok(courseService.listTutorCourses(tutorId));
    }

    @PostMapping("/{courseId}/enrollments")
    public ResponseEntity<Void> enroll(
            @PathVariable Long courseId,
            @Valid @RequestBody EnrollmentRequest request) {
        log.info("Enrolling student {} into course {}", request.studentId(), courseId);
        enrollmentService.enroll(courseId, request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponse> updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody CourseCreateRequest request) {
        log.info("Updating course with id: {}", id);
        CourseResponse response = courseService.updateCourse(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        log.info("Deleting course with id: {}", id);
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CourseResponse>> getAllCourses() {
        log.info("Fetching all courses");
        return ResponseEntity.ok(courseService.getAllCourses());
    }
}
