package com.example.SE_project.controller;


import com.example.SE_project.entity.Student;
import com.example.SE_project.service.paymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@CrossOrigin("*")
@AllArgsConstructor
public class bkpayController {

    private paymentService paymentService;
    @PutMapping()
    private ResponseEntity<?> updatePageForStudent(@RequestParam String std_id , @RequestParam Integer nb_of_page){
        return new ResponseEntity<>(paymentService.buy_more_page(nb_of_page , std_id) , HttpStatus.OK);

    }


}
