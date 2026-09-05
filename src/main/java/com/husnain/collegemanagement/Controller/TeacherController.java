package com.husnain.collegemanagement.Controller;

import com.husnain.collegemanagement.Dto.request.TeacherRequestDto;
import com.husnain.collegemanagement.Dto.response.TeacherResponseDto;
import com.husnain.collegemanagement.Dto.update.TeacherUpdateDto;
import com.husnain.collegemanagement.Entity.Teacher;
import com.husnain.collegemanagement.Service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponseDto> findTeacherById(@PathVariable long id) {
        TeacherResponseDto teacher = teacherService.findById(id);
        if (teacher != null) {
            return ResponseEntity.ok(teacher);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<TeacherResponseDto> createTeacher(@Valid @RequestBody TeacherRequestDto teacher) {
        TeacherResponseDto createdTeacher =
                teacherService.createTeacher(teacher);
        return ResponseEntity.status(201).body(createdTeacher);
    }
    @PutMapping("/{id}")
    public ResponseEntity<TeacherResponseDto> updateTeacher(@PathVariable Long id,@Valid @RequestBody TeacherUpdateDto teacherUpdateDto) {
       TeacherResponseDto updatedTeacher = teacherService.updateTeacher(id, teacherUpdateDto);
        return ResponseEntity.ok(updatedTeacher);
    }

    @GetMapping
    public ResponseEntity<List<TeacherResponseDto>> findAllTeachers() {
        List<TeacherResponseDto> teachers = teacherService.findAllTeachers();
        return ResponseEntity.ok(teachers);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeacherById(@PathVariable Long id) {
        teacherService.deleteTeacherById(id);
        return ResponseEntity.ok("Teacher deleted successfully");
    }

}


