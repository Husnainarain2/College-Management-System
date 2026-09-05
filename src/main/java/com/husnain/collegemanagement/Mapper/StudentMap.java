package com.husnain.collegemanagement.Mapper;

import com.husnain.collegemanagement.Dto.request.StudentRequestDto;
import com.husnain.collegemanagement.Dto.response.StudentResponseDto;
import com.husnain.collegemanagement.Entity.Department;
import com.husnain.collegemanagement.Entity.Student;
import com.husnain.collegemanagement.Repository.DepartmentRepository;
import com.husnain.collegemanagement.Service.DepartmentService;
import org.springframework.stereotype.Component;

@Component
public class StudentMap {
    private final DepartmentService departmentService;

    public StudentMap(DepartmentService departmentService) {
        this.departmentService = departmentService;
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
