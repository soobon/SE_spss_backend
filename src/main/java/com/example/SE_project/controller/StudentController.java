package com.example.SE_project.controller;

import com.example.SE_project.dto.StudentDTO;
import com.example.SE_project.entity.Student;
import com.example.SE_project.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/student")
@RestController
@CrossOrigin("*")
@AllArgsConstructor
public class StudentController {

    private StudentService studentService ;

    @GetMapping("/all")
    public ResponseEntity<List<StudentDTO>> allStudent(){
        return new ResponseEntity<>(studentService.allStudents(), HttpStatus.OK);
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<?> getInfo(@PathVariable String id){
        return new ResponseEntity<>(studentService.getInfo(id),HttpStatus.OK);
    }

    @GetMapping("/statistic/{id}/{month}")
    public ResponseEntity<?> getStatistic(@PathVariable String id,@PathVariable Integer month){
        return new ResponseEntity<>(studentService.getStatistic(id,month),HttpStatus.OK);
    }

    @GetMapping("/file_list/{id}")
    public ResponseEntity<?> getFiles(@PathVariable String id){
        return new ResponseEntity<>(studentService.getFiles(id),HttpStatus.OK);
    }

    @GetMapping("/history/{id}")
    public ResponseEntity<?> getHistory(@PathVariable String id){
        return new ResponseEntity<>(studentService.getHistory(id),HttpStatus.OK);
    }

}