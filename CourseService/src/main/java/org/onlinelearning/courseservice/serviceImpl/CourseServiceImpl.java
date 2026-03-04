package org.onlinelearning.courseservice.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.onlinelearning.courseservice.dto.CourseDTO;
import org.onlinelearning.courseservice.entity.Course;
import org.onlinelearning.courseservice.repository.CourseRepository;
import org.onlinelearning.courseservice.service.CourseService;
import org.onlinelearning.courseservice.config.SecurityUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    public CourseDTO createCourse(CourseDTO courseDTO) {
        String role = SecurityUtil.getCurrentUserRole();
        Long userId = SecurityUtil.getCurrentUserId();

        if (!role.equals("TEACHER") && !role.equals("ADMIN")) {
            throw new RuntimeException("Forbidden: Only TEACHER or ADMIN can create courses");
        }

        if (role.equals("TEACHER")) {
            courseDTO.setTeacherId(userId);
        }

        Course course = Course.builder()
                .title(courseDTO.getTitle())
                .description(courseDTO.getDescription())
                .teacherId(courseDTO.getTeacherId())
                .price(courseDTO.getPrice())
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .build();

        return mapToDTO(courseRepository.save(course));
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        return courseRepository.findByIsActiveTrue()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CourseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (!course.getIsActive()) throw new RuntimeException("Course is inactive");

        return mapToDTO(course);
    }

    @Override
    public CourseDTO updateCourse(Long id, CourseDTO courseDTO) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (!course.getIsActive()) throw new RuntimeException("Course is inactive");

        String role = SecurityUtil.getCurrentUserRole();
        Long userId = SecurityUtil.getCurrentUserId();

        if (role.equals("TEACHER") && !course.getTeacherId().equals(userId)) {
            throw new RuntimeException("Forbidden: Teacher can only update their own course");
        }

        if (courseDTO.getTitle() != null && !courseDTO.getTitle().isBlank()) {
            course.setTitle(courseDTO.getTitle());
        }
        if (courseDTO.getDescription() != null && !courseDTO.getDescription().isBlank()) {
            course.setDescription(courseDTO.getDescription());
        }
        if (courseDTO.getPrice() != null) {
            course.setPrice(courseDTO.getPrice());
        }

        return mapToDTO(courseRepository.save(course));
    }

    @Override
    public void softDeleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        String role = SecurityUtil.getCurrentUserRole();
        Long userId = SecurityUtil.getCurrentUserId();

        if (role.equals("TEACHER") && !course.getTeacherId().equals(userId)) {
            throw new RuntimeException("Forbidden: Teacher can only delete their own course");
        }

        course.setIsActive(false);
        courseRepository.save(course);
    }

    @Override
    public void hardDeleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        String role = SecurityUtil.getCurrentUserRole();

        if (!role.equals("ADMIN")) {
            throw new RuntimeException("Forbidden: Only ADMIN can hard delete");
        }

        courseRepository.delete(course);
    }

    private CourseDTO mapToDTO(Course course) {
        return CourseDTO.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .teacherId(course.getTeacherId())
                .price(course.getPrice())
                .isActive(course.getIsActive())
                .createdAt(course.getCreatedAt())
                .build();
    }
}