package com.husnain.collegemanagement.Repository;

import com.husnain.collegemanagement.Entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByEmail(String email);
    Page<Student> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
