package com.husnain.collegemanagement.Entity;

import jakarta.persistence.*;

@Entity
public class Student {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String age;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

}
