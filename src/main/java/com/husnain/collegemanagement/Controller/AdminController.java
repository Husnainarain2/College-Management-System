package com.husnain.collegemanagement.Controller;

import com.husnain.collegemanagement.Dto.request.TeacherRequestDto;
import com.husnain.collegemanagement.Dto.response.StudentResponseDto;
import com.husnain.collegemanagement.Dto.response.TeacherResponseDto;
import com.husnain.collegemanagement.Dto.update.StudentUpdateDto;
import com.husnain.collegemanagement.Dto.update.TeacherUpdateDto;
import com.husnain.collegemanagement.Entity.Department;
import com.husnain.collegemanagement.Security.CustomUserDetailsService;
import com.husnain.collegemanagement.Service.CourseService;
import com.husnain.collegemanagement.Service.DepartmentService;
import com.husnain.collegemanagement.Service.StudentService;
import com.husnain.collegemanagement.Service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
public class AdminController {

    private final CustomUserDetailsService userDetailsService;
    private final StudentService  studentService;
    private final CourseService courseService;
    private final TeacherService teacherService;
    private final DepartmentService departmentService;

    public AdminController(CustomUserDetailsService userDetailsService,
                           StudentService studentService,
                           CourseService courseService,
                           TeacherService teacherService,
                           DepartmentService departmentService) {
        this.userDetailsService = userDetailsService;
        this.studentService = studentService;
        this.courseService = courseService;
        this.teacherService = teacherService;
        this.departmentService = departmentService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<String> dashboard() {
        return ResponseEntity.ok("Welcome to Admin dashboard");
    }
    @PostMapping("/departments")
    public ResponseEntity<String> departments(@RequestBody Department department) {
        departmentService.createDepartment(department);
        return ResponseEntity.ok("Create department successfully");
    }
    @PutMapping("/departments/{id}")
    public ResponseEntity<String> updateDepartment(@PathVariable Long id,
                                                   @RequestBody Department department) {
        departmentService.updateDepartment(id,department);
        return ResponseEntity.ok("update  Department successfully");
    }
    @PostMapping("/teachers")
    public ResponseEntity<String> teachers(@RequestBody TeacherRequestDto teacherRequestDto) {
        teacherService.createTeacher(teacherRequestDto);
        return ResponseEntity.ok("Create teacher successfully");
    }
    @PutMapping("/teachers/{id}")
    public ResponseEntity<TeacherResponseDto> updateTeacher(@PathVariable Long id, @RequestBody TeacherUpdateDto teacherRequestDto) {
       TeacherResponseDto updateDto= teacherService.updateTeacher(id,teacherRequestDto);
       return ResponseEntity.ok(updateDto);
    }
    @DeleteMapping("/teachers/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacherById(id);
        return ResponseEntity.ok("Delete teacher successfully");
    }
    @GetMapping("/students/{id}")
    public ResponseEntity<StudentResponseDto> findStudentById(@PathVariable Long id) {
        StudentResponseDto student =
                studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }
    @PutMapping("/students/{id}")
    public ResponseEntity<StudentResponseDto> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentUpdateDto studentDetails) {
        StudentResponseDto updatedStudent =
                studentService.updateStudent(id, studentDetails);
        return ResponseEntity.ok(updatedStudent);
    }
    @DeleteMapping("/students/{id}")
    public ResponseEntity<String> deleteStudentById(@PathVariable Long id) {
        studentService.deleteStudentById(id);
        return ResponseEntity.ok("Student deleted successfully");
    }
    @GetMapping("/students/search")
    public ResponseEntity<Page<StudentResponseDto>> findStudentsByName(@RequestParam String name, Pageable pageable) {
        Page<StudentResponseDto> searchStudent=studentService.searchStudent(name,pageable);
        return ResponseEntity.ok(searchStudent);
    }

}
