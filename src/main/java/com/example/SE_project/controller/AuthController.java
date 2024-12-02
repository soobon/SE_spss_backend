package com.example.SE_project.controller;

import com.example.SE_project.dto.AuthRequest;
import com.example.SE_project.dto.RegisterRequest;
import com.example.SE_project.service.AuthService;
import com.example.SE_project.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private AuthService authService;

    private JwtService jwtService;

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

    @GetMapping("/userId")
    public ResponseEntity<?> getUserId(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            Claims claims = jwtService.newExtractAllClaims(token);
            return new ResponseEntity<>(claims.get("user_id",String.class), HttpStatus.OK);
        }
        throw new RuntimeException("Authorization header is missing or invalid");
    }
}
