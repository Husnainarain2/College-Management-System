package com.husnain.collegemanagement.Dto.response;

import com.husnain.collegemanagement.Entity.Role;

public class LoginResponseDto {
    private String token;
    private String username;
    private String role;


    public LoginResponseDto(String token, String username, String role) {
        this.token = token;
        this.username = username;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}
