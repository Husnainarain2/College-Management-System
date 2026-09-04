package com.husnain.collegemanagement.Controller;

import com.husnain.collegemanagement.Dto.request.StudentRequestDto;
import com.husnain.collegemanagement.Dto.response.StudentResponseDto;
import com.husnain.collegemanagement.Dto.update.StudentUpdateDto;
import com.husnain.collegemanagement.Entity.Student;
import com.husnain.collegemanagement.Service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @PostMapping
    public ResponseEntity<StudentResponseDto> createStudent(@Valid @RequestBody StudentRequestDto student) {
        StudentResponseDto createdStudent = studentService.createStudent(student);
        return ResponseEntity.ok(createdStudent);
    }
    @GetMapping
    public ResponseEntity<List<StudentResponseDto>> findAllStudents() {
        List<StudentResponseDto> students =
                studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDto> findStudentById(@PathVariable Long id) {
        StudentResponseDto student =
                studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }
    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDto> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentUpdateDto studentDetails) {
        StudentResponseDto updatedStudent =
                studentService.updateStudent(id, studentDetails);
        return ResponseEntity.ok(updatedStudent);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudentById(@PathVariable Long id) {
        studentService.deleteStudentById(id);
        return ResponseEntity.ok("Student deleted successfully");
    }
}
