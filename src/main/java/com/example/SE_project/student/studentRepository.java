package com.example.SE_project.student;

import com.example.SE_project.student.student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface studentRepository extends JpaRepository<student,Integer> {
}
