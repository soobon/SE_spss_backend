package com.example.SE_project.controller;

import com.example.SE_project.entity.Student;
import com.example.SE_project.service.studentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping
@RestController
public class studentController {
    @Autowired
    private studentService studentService ;

    @GetMapping
    public List<Student> allStudent(){
        return studentService.allStudents();
    }

}