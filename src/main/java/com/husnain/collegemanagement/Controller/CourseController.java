package com.husnain.collegemanagement.Controller;

import com.husnain.collegemanagement.Entity.Course;
import com.husnain.collegemanagement.Service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }
    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course courseDetails) {
        Course createdCourse = courseService.createCourse(courseDetails);
        return ResponseEntity.ok(createdCourse);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Course> findCourseById(@PathVariable Long id) {
        Course course = courseService.findById(id);
        return ResponseEntity.ok(course);
    }
    @GetMapping
    public ResponseEntity<List<Course>> findAllCourses() {
        List<Course> courses = courseService.findAllCourses();
        return ResponseEntity.ok(courses);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course courseDetails) {
        Course updatedCourse = courseService.updateCourse(id, courseDetails);
        return ResponseEntity.ok(updatedCourse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourseById(@PathVariable Long id) {
        courseService.deleteCourseById(id);
        return ResponseEntity.ok("Course deleted successfully");
    }
}
