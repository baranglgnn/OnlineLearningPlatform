package org.onlinelearning.courseservice.restApiController;

import lombok.RequiredArgsConstructor;
import org.onlinelearning.courseservice.dto.CourseDTO;
import org.onlinelearning.courseservice.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping("/create")
    public ResponseEntity<CourseDTO> createCourse(@RequestBody CourseDTO courseDTO) {
        return ResponseEntity.ok(courseService.createCourse(courseDTO));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long id,
                                                  @RequestBody CourseDTO courseDTO) {
        return ResponseEntity.ok(courseService.updateCourse(id, courseDTO));
    }

    @DeleteMapping("/softDelete/{id}")
    public ResponseEntity<String> softDeleteCourse(@PathVariable Long id) {
        courseService.softDeleteCourse(id);
        return ResponseEntity.ok("Course soft deleted");
    }

    @DeleteMapping("/hardDelete/{id}")
    public ResponseEntity<String> hardDeleteCourse(@PathVariable Long id) {
        courseService.hardDeleteCourse(id);
        return ResponseEntity.ok("Course hard deleted");
    }
}