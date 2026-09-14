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
import org.springframework.boot.data.autoconfigure.web.DataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public org.springframework.data.domain.Page<StudentResponseDto> getAllStudents(Pageable pageable) {
        org.springframework.data.domain.Page<Student> students = studentRepository.findAll(pageable);
        return students.map(this::mapToDto);
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
    public StudentResponseDto getStudentByUserName(String username) {
        Student s1=studentRepository.getStudentByname(username).orElseThrow(() -> new ResourceNotFoundException("Student not found with username "+username));
        return mapToDto(s1);
    }

    public Page<StudentResponseDto> searchStudent(String name,Pageable  pageable) {
        Page<Student> students =
                studentRepository.findByNameContainingIgnoreCase(name,pageable);
        if (students.isEmpty()) {
            throw new ResourceNotFoundException("Students not found with name " + name);
        }
        return students.map(this::mapToDto);
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
