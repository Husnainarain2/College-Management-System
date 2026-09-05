package com.husnain.collegemanagement.Service;

import com.husnain.collegemanagement.Entity.Department;
import com.husnain.collegemanagement.Exceptions.DuplicateResourceException;
import com.husnain.collegemanagement.Exceptions.ResourceNotFoundException;
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
        if (existByName(department)) {
            throw new DuplicateResourceException(department.getName()+"Department are already exist");
        }
        return departmentRepository.save(department);
    }

    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department not found with id"+id));
    }
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
    public Department updateDepartment(Long id, Department departmentDetails) {
        Department department =
                departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department not found with id "+id));
        department.setId(departmentDetails.getId());
        department.setName(departmentDetails.getName());
        departmentRepository.save(department);
        return department;
    }
    public void deleteDepartmentById(Long id) {
        Department department =
                departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department not found with "+id));
        departmentRepository.delete(department);
    }

    public boolean existByName(Department department) {
        return departmentRepository.existsByName(department.getName());
    }
}
