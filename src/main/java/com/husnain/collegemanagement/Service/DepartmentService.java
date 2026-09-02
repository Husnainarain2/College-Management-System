package com.husnain.collegemanagement.Service;

import com.husnain.collegemanagement.Entity.Department;
import com.husnain.collegemanagement.Repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private DepartmentRepository departmentRepository;
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Department not found"));
    }
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
    public Department updateDepartment(Long id, Department departmentDetails) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Department not found"));
        department.setId(departmentDetails.getId());
        department.setName(departmentDetails.getName());
        departmentRepository.save(department);
        return department;
    }
    public void deleteDepartmentById(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Department not found"));
        departmentRepository.delete(department);
    }
}
