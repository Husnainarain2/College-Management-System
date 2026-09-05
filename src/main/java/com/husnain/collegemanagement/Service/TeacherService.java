package com.husnain.collegemanagement.Service;

import com.husnain.collegemanagement.Dto.request.TeacherRequestDto;
import com.husnain.collegemanagement.Dto.response.TeacherResponseDto;
import com.husnain.collegemanagement.Dto.update.TeacherUpdateDto;
import com.husnain.collegemanagement.Entity.Teacher;
import com.husnain.collegemanagement.Repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final DepartmentService departmentService;

    public TeacherService(TeacherRepository teacherRepository, DepartmentService departmentService) {
        this.teacherRepository = teacherRepository;
        this.departmentService = departmentService;
    }
    public List<TeacherResponseDto> findAllTeachers() {
        return teacherRepository.findAll().stream().map(this::mapToResponseDto).collect(java.util.stream.Collectors.toList());
    }

    public TeacherResponseDto createTeacher(TeacherRequestDto teacherRequestDto) {
        Teacher teacher = mapToEntity(teacherRequestDto);
        return mapToResponseDto(teacherRepository.save(teacher));
    }

    public TeacherResponseDto findById(long id) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new RuntimeException("Teacher not found"));
        return mapToResponseDto(teacher);
    }

    public TeacherResponseDto updateTeacher(Long id,
                           TeacherUpdateDto teacher) {
        var existingTeacher = teacherRepository.findById(id).orElseThrow(() -> new RuntimeException("Teacher not found"));
        existingTeacher.setEmail(teacher.getEmail());
        teacherRepository.save(existingTeacher);
        return mapToResponseDto(existingTeacher);
    }

    public void deleteTeacherById(Long id) {
        teacherRepository.deleteById(id);
    }


    public Teacher mapToEntity(TeacherRequestDto teacherRequestDto) {
        Teacher teacher = new Teacher();
        teacher.setName(teacherRequestDto.getName());
        teacher.setEmail(teacherRequestDto.getEmail());
        // Assuming you have a method to find the department by ID
         teacher.setDepartment(departmentService.getDepartmentById(teacherRequestDto.getDepartmentId()));
        return teacherRepository.save(teacher);
    }

    public TeacherResponseDto mapToResponseDto(Teacher teacher) {
        TeacherResponseDto responseDto = new TeacherResponseDto();
        responseDto.setId(teacher.getId());
        responseDto.setName(teacher.getName());
        responseDto.setDepartmentName(teacher.getDepartment().getName());
        return responseDto;
    }
}