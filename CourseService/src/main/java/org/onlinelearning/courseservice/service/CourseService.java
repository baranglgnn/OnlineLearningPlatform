package org.onlinelearning.courseservice.service;

import org.onlinelearning.courseservice.dto.CourseDTO;
import java.util.List;

public interface CourseService {

    CourseDTO createCourse(CourseDTO courseDTO);
    List<CourseDTO> getAllCourses();
    CourseDTO getCourseById(Long id);
    CourseDTO updateCourse(Long id, CourseDTO courseDTO);
    void softDeleteCourse(Long id);
    void hardDeleteCourse(Long id);
}