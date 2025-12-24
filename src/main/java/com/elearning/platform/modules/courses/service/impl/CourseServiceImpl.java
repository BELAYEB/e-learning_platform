package com.elearning.platform.modules.courses.service.impl;

import com.elearning.platform.common.exception.ResourceNotFoundException;
import com.elearning.platform.modules.courses.api.dto.CourseCreateRequest;
import com.elearning.platform.modules.courses.api.dto.CourseResponse;
import com.elearning.platform.modules.courses.domain.model.Course;
import com.elearning.platform.modules.courses.domain.repository.CourseRepository;
import com.elearning.platform.modules.courses.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    public CourseResponse createCourse(CourseCreateRequest request) {
        Course course = Course.builder()
                .title(request.title())
                .description(request.description())
                .tutorId(request.tutorId())
                .capacity(request.capacity())
                .active(true)
                .build();

        Course saved = courseRepository.save(course);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CourseResponse getCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        return toResponse(course);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseResponse> listTutorCourses(Long tutorId) {
        return courseRepository.findByTutorId(tutorId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public CourseResponse updateCourse(Long id, CourseCreateRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        course.setTitle(request.title());
        course.setDescription(request.description());
        course.setCapacity(request.capacity());
        course.setTutorId(request.tutorId());

        Course updated = courseRepository.save(course);
        return toResponse(updated);
    }

    @Override
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course not found");
        }
        courseRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private CourseResponse toResponse(Course c) {
        return new CourseResponse(
                c.getId(),
                c.getTitle(),
                c.getDescription(),
                c.getTutorId(),
                c.getCapacity(),
                c.getActive()
        );
    }
}
