package com.example.SE_project.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class studentService {
    @Autowired
    private studentRepository studentRepository;

    public List<student> allStudents(){
        return studentRepository.findAll();
    }
}
