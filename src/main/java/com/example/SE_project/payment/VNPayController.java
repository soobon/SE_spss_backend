package com.example.SE_project.payment;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/vnpay")
@AllArgsConstructor
public class VNPayController {

    private final VNPayService service;

    @GetMapping("/pay")
    public ResponseEntity<String> pay(
            HttpServletRequest req,
            @RequestParam Float amount
    ) throws Exception {
        return new ResponseEntity<>(service.pay(req, amount), HttpStatus.OK);
    }

    @GetMapping("/return")
    public ResponseEntity<String> returnUrl(HttpServletRequest req){
        try {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", service.returnUrl(req))
                    .body(null);
        } catch (Exception e) {
            throw new RuntimeException("Payment failed");
        }
    }
}
