package com.husnain.collegemanagement.Service;

import com.husnain.collegemanagement.Dto.request.TeacherRequestDto;
import com.husnain.collegemanagement.Dto.response.TeacherResponseDto;
import com.husnain.collegemanagement.Dto.update.TeacherUpdateDto;
import com.husnain.collegemanagement.Entity.Role;
import com.husnain.collegemanagement.Entity.Student;
import com.husnain.collegemanagement.Entity.Teacher;
import com.husnain.collegemanagement.Entity.User;
import com.husnain.collegemanagement.Exceptions.ResourceNotFoundException;
import com.husnain.collegemanagement.Repository.TeacherRepository;
import com.husnain.collegemanagement.Repository.UserRepository;
import com.husnain.collegemanagement.Security.JwtService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final DepartmentService departmentService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository  userRepository;

    public TeacherService(TeacherRepository teacherRepository, DepartmentService departmentService,
                          PasswordEncoder passwordEncoder,
                         UserRepository  userRepository) {
        this.teacherRepository = teacherRepository;
        this.departmentService = departmentService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }
    public Page<TeacherResponseDto> findAllTeachers(Pageable  pageable) {
        Page<Teacher> teachers = teacherRepository.findAll(pageable);
        return teachers.map(this::mapToResponseDto);
    }

    public TeacherResponseDto createTeacher(TeacherRequestDto teacherRequestDto) {
        Teacher teacher = mapToEntity(teacherRequestDto);
        if (existsByEmail(teacherRequestDto.getEmail())) {
            throw new ResourceNotFoundException("Teacher not found with name"+teacher.getName());
        }
        User user=new User();
        user.setId(teacher.getId());
        user.setEmail(teacherRequestDto.getEmail());
        user.setUsername(teacher.getName());
        user.setRole(Role.TEACHER);
        user.setPassword(passwordEncoder.encode(teacherRequestDto.getPassword()));
        userRepository.save(user);
        return mapToResponseDto(teacherRepository.save(teacher));
    }

    public TeacherResponseDto findById(long id) {
        Teacher teacher =
                teacherRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id "+id));
        return mapToResponseDto(teacher);
    }

    public TeacherResponseDto updateTeacher(Long id,
                           TeacherUpdateDto teacher) {
        var existingTeacher =
                teacherRepository.findById(id).orElseThrow(() -> new RuntimeException("Teacher not found with id "+id));
        existingTeacher.setEmail(teacher.getEmail());
        teacherRepository.save(existingTeacher);
        return mapToResponseDto(existingTeacher);
    }

    public void deleteTeacherById(Long id) {
        teacherRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id "+id));
        teacherRepository.deleteById(id);
    }
    public Page<TeacherResponseDto> searchTeachers(String name,Pageable pageable) {
        Page<Teacher> teachers  = teacherRepository.findByNameContainingIgnoreCase(name, pageable);
        if (teachers.isEmpty()) {
            throw new ResourceNotFoundException("Teacher not found with name "+name);
        }
        return teachers.map(this::mapToResponseDto);
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


    public boolean existsByEmail(String email) {
        return teacherRepository.existsByEmail(email);
    }
}