package com.example.SE_project.service;

import com.example.SE_project.entity.Student;
import com.example.SE_project.reposistory.studentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class studentService {
    @Autowired
    private studentRepository studentRepository;

    public List<Student> allStudents(){
        return studentRepository.findAll();
    }
}