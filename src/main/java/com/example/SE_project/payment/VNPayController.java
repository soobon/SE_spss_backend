package com.example.SE_project.payment;

import com.example.SE_project.service.paymentService;
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

    private final VNPayService vnPayService;
    private final paymentService paymentService;

    @GetMapping("/pay")
    public ResponseEntity<String> pay(
            HttpServletRequest req,
            @RequestParam Integer numPages,
            @RequestParam String id
    ) throws Exception {
        return new ResponseEntity<>(vnPayService.pay(req, numPages, id), HttpStatus.OK);
    }

    @GetMapping("/return")
    public ResponseEntity<String> returnUrl(HttpServletRequest req){
        try {
            //update page for student
            String id = req.getParameter("id");
            String numPages = req.getParameter("numPages");
            paymentService.buy_more_page(Integer.parseInt(numPages),id);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", vnPayService.returnUrl(req))
                    .body(null);
        } catch (Exception e) {
            throw new RuntimeException("Payment failed");
        }
    }
}
