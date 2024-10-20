package com.example.SE_project.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping
@RestController
public class studentController {
    @Autowired
    private studentService studentService;

    @GetMapping
    public List<student> allStudent(){
        return studentService.allStudents();
    }

}
