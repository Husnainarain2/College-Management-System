package com.husnain.collegemanagement.Dto.request;

import com.husnain.collegemanagement.Entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class RegisterRequestDto {

    @NotBlank(message = "UserName is required")
    private String username;
    @NotBlank(message = "Email is Required")
    private String email;
    @NotBlank(message = "Password not null ")
    @Size(min = 6, message = "Password must contain at least 6 characters")
    private  String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @NotNull(message = "Role is required")
    private Role role;

}
