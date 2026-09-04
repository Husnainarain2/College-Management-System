package com.husnain.collegemanagement.Dto.update;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudentUpdateDto {
    @NotBlank(message = "Name is mandatory")
    private String name;
    @Email
    @NotBlank
    private String email;

}
