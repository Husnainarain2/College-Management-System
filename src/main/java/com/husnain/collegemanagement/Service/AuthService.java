package com.husnain.collegemanagement.Service;

import com.husnain.collegemanagement.Dto.request.LoginRequestDto;
import com.husnain.collegemanagement.Dto.request.RegisterRequestDto;
import com.husnain.collegemanagement.Dto.response.LoginResponseDto;
import com.husnain.collegemanagement.Entity.User;
import com.husnain.collegemanagement.Repository.UserRepository;
import com.husnain.collegemanagement.Security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }
    public String register(RegisterRequestDto registerRequestDto) {
        if (userRepository.existsByEmail(registerRequestDto.getEmail())) {
            throw new UsernameNotFoundException("Email already exists"+registerRequestDto.getEmail());
        }
        User user = new User();
        user.setUserName(registerRequestDto.getUsername());
        user.setEmail(registerRequestDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));

        user.setRole(registerRequestDto.getRole());
        userRepository.save(user);
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
                user.getUserName(),
                user.getRole().name()
        );
    }
}
