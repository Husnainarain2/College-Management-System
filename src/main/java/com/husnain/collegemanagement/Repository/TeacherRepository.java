package com.husnain.collegemanagement.Repository;

import com.husnain.collegemanagement.Dto.response.TeacherResponseDto;
import com.husnain.collegemanagement.Entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    boolean existsByEmail(String email);
    Page<Teacher> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
