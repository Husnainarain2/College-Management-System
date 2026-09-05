package com.husnain.collegemanagement.Service;

import com.husnain.collegemanagement.Dto.request.StudentRequestDto;
import com.husnain.collegemanagement.Dto.response.StudentResponseDto;
import com.husnain.collegemanagement.Dto.update.StudentUpdateDto;
import com.husnain.collegemanagement.Entity.Department;
import com.husnain.collegemanagement.Entity.Student;
import com.husnain.collegemanagement.Exceptions.DuplicateResourceException;
import com.husnain.collegemanagement.Exceptions.ResourceNotFoundException;
import com.husnain.collegemanagement.Mapper.StudentMap;
import com.husnain.collegemanagement.Repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMap studentMap;
    public StudentService(StudentRepository studentRepository,StudentMap studentMap ) {
        this.studentRepository = studentRepository;
        this.studentMap = studentMap;
    }

    public StudentResponseDto createStudent(StudentRequestDto studentDto) {
        Student student =
                studentMap.mapToEntity(studentDto);
        if (emailExist(student)) {
            throw new DuplicateResourceException("Student with email " + student.getEmail() + " already exists");
        }
        studentRepository.save(student);
        return mapToDto(student);
    }

    public List<StudentResponseDto> getAllStudents() {
        return studentRepository.findAll().stream().map(this::mapToDto).toList();
    }
    public StudentResponseDto getStudentById(Long id) {
        return mapToDto(studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found with id "+id)));
    }
    public StudentResponseDto updateStudent(Long id,
                                            StudentUpdateDto studentDetails) {
        Student student =
                studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found with id "+id));
        student.setId(id);
        student.setName(studentDetails.getName());
        student.setEmail(studentDetails.getEmail());
        studentRepository.save(student);
        return mapToDto(student);
    }
    public void deleteStudentById(Long id) {
        Student student =
                studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found with id "+id));
        studentRepository.delete(student);
    }



    // response mapper
    public StudentResponseDto mapToDto(Student student) {
        StudentResponseDto dto = new StudentResponseDto();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setDepartmentName(student.getDepartment().getName());
        return dto;
    }

    public boolean emailExist(Student student) {
       return studentRepository.existsByEmail(student.getEmail());
    }
}
