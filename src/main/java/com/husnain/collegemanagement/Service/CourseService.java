package com.husnain.collegemanagement.Service;

import com.husnain.collegemanagement.Entity.Course;
import com.husnain.collegemanagement.Repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    public Course findById(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
    }
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }
    public void deleteCourseById(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        courseRepository.delete(course);
    }
    public Course updateCourse(Long id, Course courseDetails) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        course.setId(courseDetails.getId());
        course.setName(courseDetails.getName());
        courseRepository.save(course);
        return course;
    }
    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }
}
