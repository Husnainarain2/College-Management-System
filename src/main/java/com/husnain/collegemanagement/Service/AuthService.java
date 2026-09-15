package com.husnain.collegemanagement.Service;

import com.husnain.collegemanagement.Dto.request.LoginRequestDto;
import com.husnain.collegemanagement.Dto.request.RegisterRequestDto;
import com.husnain.collegemanagement.Dto.response.LoginResponseDto;
import com.husnain.collegemanagement.Entity.*;
import com.husnain.collegemanagement.Exceptions.ResourceNotFoundException;
import com.husnain.collegemanagement.Repository.*;
import com.husnain.collegemanagement.Security.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final DepartmentRepository departmentRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, StudentRepository studentRepository,
                       TeacherRepository teacherRepository,
                       DepartmentRepository departmentRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService,
                       AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.departmentRepository = departmentRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.adminRepository = adminRepository;
    }

    @Transactional
    public String register(RegisterRequestDto registerRequestDto) {
        if (userRepository.existsByUsername(registerRequestDto.getUsername())) {
            throw new UsernameNotFoundException("Username " + registerRequestDto.getUsername() + " already exists");
        }
        if (userRepository.existsByEmail(registerRequestDto.getEmail())) {
            throw new UsernameNotFoundException("Email already exists"+registerRequestDto.getEmail());
        }

        User user = new User();
        user.setUsername(registerRequestDto.getUsername());
        user.setEmail(registerRequestDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        user.setRole(registerRequestDto.getRole());
        userRepository.save(user);
        if (registerRequestDto.getRole()== Role.STUDENT) {
            Department department= departmentRepository.findById(registerRequestDto.getDepartmentId()).
                    orElseThrow(()->
                    new ResourceNotFoundException("Department not found")
            );
            Student student = new Student();

            student.setName(registerRequestDto.getName());

            student.setEmail(registerRequestDto.getEmail());

            student.setDepartment(department);

            student.setUser(user);


            studentRepository.save(student);
        } else if (registerRequestDto.getRole()==Role.TEACHER) {
            Department department =
                    departmentRepository.findById(registerRequestDto.getDepartmentId())
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Department not found"
                                    )
                            );


            Teacher teacher = new Teacher();

            teacher.setName(registerRequestDto.getName());

            teacher.setEmail(registerRequestDto.getEmail());

            teacher.setDepartment(department);

            teacher.setUser(user);


            teacherRepository.save(teacher);

        } else if (registerRequestDto.getRole()==Role.ADMIN) {
            Department department =
                    departmentRepository.findById(registerRequestDto.getDepartmentId())
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Department not found"
                                    )
                            );
            Admin admin = new Admin();
            admin.setName(registerRequestDto.getName());
            admin.setEmail(registerRequestDto.getEmail());
            admin.setDepartment(department);
            admin.setUser(user);
            adminRepository.save(admin);
        }
        return "User registered successfully";
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername(),
                        loginRequestDto.getPassword()
                )
        );
        User user = userRepository.findByUsername(loginRequestDto.getUsername()).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));

        String token =jwtService.generateToken(user);

        return new LoginResponseDto(
                token,
                user.getUsername(),
                user.getRole().name()
        );
    }
}
