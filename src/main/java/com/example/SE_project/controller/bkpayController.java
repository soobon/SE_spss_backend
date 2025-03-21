package com.example.SE_project.controller;


import com.example.SE_project.entity.Student;
import com.example.SE_project.payment.VNPayService;
import com.example.SE_project.service.paymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class bkpayController {

    private paymentService paymentService;
    private VNPayService vnPayService;
    @PutMapping()
    private ResponseEntity<?> updatePageForStudent(
            @RequestParam String std_id ,
            @RequestParam Integer nb_of_page,
            HttpServletRequest request
    ){
        try {

            return new ResponseEntity<>(vnPayService.pay(request,nb_of_page,std_id), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
