package com.husnain.collegemanagement.Service;

import com.husnain.collegemanagement.Dto.request.StudentRequestDto;
import com.husnain.collegemanagement.Dto.update.StudentUpdateDto;
import com.husnain.collegemanagement.Entity.Department;
import com.husnain.collegemanagement.Entity.Student;
import com.husnain.collegemanagement.Repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    private final DepartmentService departmentService;

    public StudentService(StudentRepository studentRepository, DepartmentService departmentService) {
        this.departmentService = departmentService;
        this.studentRepository = studentRepository;
    }

    public void createStudent(StudentRequestDto studentDto) {
        Student student = mapToEntity(studentDto);
        studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
    }
    public Student updateStudent(Long id,
                                 StudentUpdateDto studentDetails) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
        student.setId(id);
        student.setName(studentDetails.getName());
        student.setEmail(studentDetails.getEmail());
        studentRepository.save(student);
        return student;
    }
    public void deleteStudentById(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
        studentRepository.delete(student);
    }




    public Student mapToEntity(StudentRequestDto student) {
        Student entity = new Student();
        entity.setName(student.getName());
        entity.setEmail(student.getEmail());
        entity.setAge(student.getAge());
        Department department = departmentService.getDepartmentById(student.getDepartmentId());
        entity.setDepartment(department);
        return entity;
    }
}
