package com.husnain.collegemanagement.Dto.update;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class TeacherUpdateDto {
    @Email
    private String email;

}
