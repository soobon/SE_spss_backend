package com.example.SE_project.controller;

import com.example.SE_project.dto.AuthRequest;
import com.example.SE_project.dto.RegisterRequest;
import com.example.SE_project.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private AuthService authService;

//    @PostMapping("/register")
//    public ResponseEntity<?> register(
//            @Valid @RequestBody RegisterRequest request
//    ){
//        return ResponseEntity.ok(authService.register(request));
//    }

    @PostMapping("/authen")
    public ResponseEntity<?> auth(
            @RequestBody AuthRequest request
    ){
        return ResponseEntity.ok(authService.auth(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(){
        return ResponseEntity.ok("Logout successfully");
    }
}
