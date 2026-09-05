package com.husnain.collegemanagement.Service;

import com.husnain.collegemanagement.Entity.Course;
import com.husnain.collegemanagement.Exceptions.DuplicateResourceException;
import com.husnain.collegemanagement.Exceptions.ResourceNotFoundException;
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
        return courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course not found with id"+id));
    }
    public Course createCourse(Course course) {
        if (existsByName(course)) {
            throw new DuplicateResourceException(course.getName()+"Course are already exist with name ");
        }
        return courseRepository.save(course);
    }
    public void deleteCourseById(Long id) {
        Course course =
                courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course not found with id"+id));
        courseRepository.delete(course);
    }
    public Course updateCourse(Long id, Course courseDetails) {
        Course course =
                courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course not found with id"+id));
        course.setId(courseDetails.getId());
        course.setName(courseDetails.getName());
        courseRepository.save(course);
        return course;
    }
    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }

    public boolean existsByName(Course course) {
        return courseRepository.existsByName(course.getName());
    }
}
