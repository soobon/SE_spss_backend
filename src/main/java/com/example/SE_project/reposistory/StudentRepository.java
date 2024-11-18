package com.example.SE_project.reposistory;

import com.example.SE_project.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentRepository extends JpaRepository<Student,Integer> {
}