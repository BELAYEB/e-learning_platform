package com.elearning.platform.modules.courses.service;

import com.elearning.platform.modules.courses.api.dto.CourseCreateRequest;
import com.elearning.platform.modules.courses.api.dto.CourseResponse;

import java.util.List;

public interface CourseService {
    CourseResponse createCourse(CourseCreateRequest request);
    CourseResponse getCourse(Long id);
    List<CourseResponse> listTutorCourses(Long tutorId);
    CourseResponse updateCourse(Long id, CourseCreateRequest request);
    void deleteCourse(Long id);
    List<CourseResponse> getAllCourses();
}
