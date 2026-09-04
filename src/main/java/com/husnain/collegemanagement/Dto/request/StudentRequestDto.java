package com.husnain.collegemanagement.Dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class StudentRequestDto {

    @NotBlank(message = "Name is required")
    private String name;

   @Email(message = "Email is not valid")
    @NotBlank(message = "Email is required")
    private String email;
    @Min(18)
    @Max(50)
    private int age;

    @NotNull
    private Long departmentId;
}
