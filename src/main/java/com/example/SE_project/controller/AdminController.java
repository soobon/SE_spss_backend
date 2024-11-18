package com.example.SE_project.controller;

import com.example.SE_project.dto.adminDTO;
import com.example.SE_project.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
@AllArgsConstructor
public class AdminController {

    private AdminService adminService ;

    @GetMapping("/{id}")
    public ResponseEntity<adminDTO> find_name_email_admin(@PathVariable String id){
        return new ResponseEntity<>(adminService.find_admin_information(id), HttpStatus.OK);
    }

    @GetMapping("/getAllPrinter")
    public ResponseEntity<?> getALLprinter(){
        return new ResponseEntity<>(adminService.get_all_printer() , HttpStatus.OK);
    }

}
