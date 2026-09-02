package com.husnain.collegemanagement.Service;

import com.husnain.collegemanagement.Entity.Teacher;
import com.husnain.collegemanagement.Repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }
    public List<Teacher> findAllTeachers() {
        return teacherRepository.findAll();
    }

    public Teacher createTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public Teacher findById(long id) {
        return teacherRepository.findById(id).orElseThrow(() -> new RuntimeException("Teacher not found"));

    }

    public Teacher updateTeacher(Long id,
                           Teacher teacher) {
        var existingTeacher = teacherRepository.findById(id).orElseThrow(() -> new RuntimeException("Teacher not found"));
        existingTeacher.setName(teacher.getName());
        existingTeacher.setEmail(teacher.getEmail());
        teacherRepository.save(existingTeacher);
        return existingTeacher;
    }

    public void deleteTeacherById(Long id) {
        teacherRepository.deleteById(id);
    }
}
